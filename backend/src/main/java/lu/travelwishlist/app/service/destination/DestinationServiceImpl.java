package lu.travelwishlist.app.service.destination;

import jakarta.transaction.Transactional;
import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;
import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.DestinationTag;
import lu.travelwishlist.app.entity.Tag;
import lu.travelwishlist.app.mapper.DestinationMapper;
import lu.travelwishlist.app.repository.DestinationRepository;
import lu.travelwishlist.app.repository.DestinationTagRepository;
import lu.travelwishlist.app.repository.WishlistRepository;
import lu.travelwishlist.app.service.country.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<DestinationResponseDTO> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(this::mapToDtoWithTags)
                .collect(Collectors.toList());
    }

    @Override
    public DestinationResponseDTO getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        return mapToDtoWithTags(destination);
    }

    @Override
    public DestinationResponseDTO createDestination(CreateDestinationRequestDTO dto) {
        Country country = countryService.getCountryById(dto.getCountryId());

        Destination destination = DestinationMapper.toEntity(dto, country);
        destinationRepository.save(destination);

        return mapToDtoWithTags(destination);
    }

    @Override
    public DestinationResponseDTO updateDestination(Long id, UpdateDestinationRequestDTO dto) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));

        Country country = countryService.getCountryById(dto.getCountryId());
        DestinationMapper.updateEntity(destination, dto, country);

        destinationRepository.save(destination);
        return mapToDtoWithTags(destination);
    }


    @Override
    @Transactional
    public void deleteDestination(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));

        // Remove tag associations first
        destinationTagRepository.deleteAll(
                destinationTagRepository.findByDestinationOrderByTagNameAsc(destination)
        );

        // Remove wishlist entries referencing this destination
        wishlistRepository.deleteAll(
                wishlistRepository.findByDestination(destination)
        );

        // Now safe to delete destination
        destinationRepository.delete(destination);
    }


    // Helper method
    private DestinationResponseDTO mapToDtoWithTags(Destination destination) {
        List<Tag> tags = destinationTagRepository
                .findByDestinationOrderByTagNameAsc(destination).stream()
                .map(DestinationTag::getTag)
                .collect(Collectors.toList());

        return DestinationMapper.toDto(destination, tags);
    }

}
