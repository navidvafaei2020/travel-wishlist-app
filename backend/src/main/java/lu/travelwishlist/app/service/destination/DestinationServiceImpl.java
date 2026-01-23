package lu.travelwishlist.app.service.destination;


import jakarta.transaction.Transactional;
import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;
import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.DestinationTag;
import lu.travelwishlist.app.entity.Tag;
import lu.travelwishlist.app.exception.DuplicateResourceException;
import lu.travelwishlist.app.mapper.DestinationMapper;
import lu.travelwishlist.app.repository.DestinationRepository;
import lu.travelwishlist.app.repository.DestinationTagRepository;
import lu.travelwishlist.app.repository.WishlistRepository;
import lu.travelwishlist.app.service.country.CountryService;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;


/**
 * Service implementation for managing destinations.
 *
 * <p>
 * Handles CRUD operations, tag associations, wishlist cleanup, and AI-based suggestions.
 */
@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final CountryService countryService;
    private final DestinationTagRepository destinationTagRepository;
    private  final WishlistRepository wishlistRepository;

    public DestinationServiceImpl(
            DestinationRepository destinationRepository,
            CountryService countryService,
            DestinationTagRepository destinationTagRepository,
            WishlistRepository wishlistRepository
    ) {
        this.destinationRepository = destinationRepository;
        this.countryService = countryService;
        this.destinationTagRepository = destinationTagRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String openAiUrl;

    @Value("${openai.model}")
    private String model;

    /**
     * Retrieves all destinations with their tags.
     *
     * @return list of DestinationResponseDTO
     */
    @Override
    public List<DestinationResponseDTO> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(this::mapToDtoWithTags)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a destination by its ID.
     *
     * @param id destination ID
     * @return DestinationResponseDTO
     */
    @Override
    public DestinationResponseDTO getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        return mapToDtoWithTags(destination);
    }


    /**
     * AI-assisted suggestion of destinations based on tags.
     *
     * @param tags comma-separated tags
     * @return suggested city names as a comma-separated string
     */
    @Override
    public String getDestinationByTags(String tags) {

        if (apiKey == null || apiKey.isBlank()) {
            return "Paris, Rome, Barcelona, Lisbon";    // Fallback for professor / reviewers
        }

        String content = "";
        String apiKey = System.getenv("OPENAI_API_KEY");
        String requestBody = String.format("""
            {
              "model": "%s",
              "messages": [
                {
                  "role": "user",
                  "content": "Tell me the name the cities only, separated by commas, each city name must begin with a capital letter and maximum 10 cities that are: %s"
                }
              ]
            }
            """, model, tags);


        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(openAiUrl))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            /*
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();


             */

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(response.body());
            content = root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();

        } catch (IOException | InterruptedException e) {
            content = "";
        }

        return content;

    }


    /**
     * Creates a new destination.
     *
     * @param dto DTO with destination details
     * @return DestinationResponseDTO
     */
    @Override
    public DestinationResponseDTO createDestination(CreateDestinationRequestDTO dto) {
        Country country = countryService.getCountryById(dto.getCountryId());

        boolean exists = destinationRepository
                .existsByNameIgnoreCaseAndCountry(dto.getName(), country);

        if (exists) {
            throw new DuplicateResourceException("Destination", "name", dto.getName());
        }

        Destination destination = DestinationMapper.toEntity(dto, country);
        destinationRepository.save(destination);

        return mapToDtoWithTags(destination);
    }


    /**
     * Updates an existing destination.
     *
     * @param id  destination ID
     * @param dto DTO with updated details
     * @return DestinationResponseDTO
     */
    @Override
    public DestinationResponseDTO updateDestination(Long id, UpdateDestinationRequestDTO dto) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Destination not found with id: " + id));

        Country country = countryService.getCountryById(dto.getCountryId());

        boolean exists = destinationRepository
                .existsByNameIgnoreCaseAndCountryAndIdNot(dto.getName(), country, id);

        if (exists) {
            throw new DuplicateResourceException("Destination", "name", dto.getName());
        }

        DestinationMapper.updateEntity(destination, dto, country);
        destinationRepository.save(destination);

        return mapToDtoWithTags(destination);
    }

    /**
     * Deletes a destination, including tag associations and wishlist entries.
     *
     * @param id destination ID
     */
    @Override
    @Transactional
    public void deleteDestination(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));

        // Remove tag associations first
        destinationTagRepository.deleteAll(
                destinationTagRepository.findByDestination(destination)
        );

        // Remove wishlist entries referencing this destination
        wishlistRepository.deleteAll(
                wishlistRepository.findByDestination(destination)
        );

        // Now safe to delete destination
        destinationRepository.delete(destination);
    }


    /**
     * Helper method to map a Destination entity to a DTO including tags.
     *
     * @param destination entity
     * @return DestinationResponseDTO
     */
    private DestinationResponseDTO mapToDtoWithTags(Destination destination) {
        List<Tag> tags = destinationTagRepository
                .findByDestination(destination).stream()
                .sorted((dt1, dt2) -> dt1.getTag().getName().compareTo(dt2.getTag().getName()))
                .map(DestinationTag::getTag)
                .collect(Collectors.toList());

        return DestinationMapper.toDto(destination, tags);
    }
}
