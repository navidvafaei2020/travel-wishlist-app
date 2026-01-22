package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;
import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Destination entities and DTOs.
 *
 * <p>
 * Provides methods for entity → DTO conversion, DTO → entity creation,
 * and updating existing entities from DTOs.
 */
public class DestinationMapper {

    /**
     * Converts a Destination entity and its associated tags to a DestinationResponseDTO.
     *
     * @param destination the Destination entity
     * @param tags the list of associated Tag entities
     * @return a DestinationResponseDTO with destination details and tag names
     */
    public static DestinationResponseDTO toDto(Destination destination, List<Tag> tags) {
        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        return new DestinationResponseDTO(
                destination.getId(),
                destination.getName(),
                destination.getDescription(),
                destination.getImageUrl(),
                destination.getCountry().getId(),
                destination.getCountry().getName(),
                tagNames
        );
    }


    /**
     * Converts a CreateDestinationRequestDTO to a new Destination entity.
     *
     * @param dto the creation DTO
     * @param country the associated Country entity
     * @return a new Destination entity
     */
    public static Destination toEntity(CreateDestinationRequestDTO dto, Country country) {
        Destination destination = new Destination();
        destination.setName(dto.getName());
        destination.setDescription(dto.getDescription());
        destination.setImageUrl(dto.getImageUrl());
        destination.setCountry(country);
        return destination;
    }



    /**
     * Updates an existing Destination entity from an UpdateDestinationRequestDTO.
     *
     * @param destination the Destination entity to update
     * @param dto the update DTO
     * @param country the updated Country entity
     */
    public static void updateEntity(Destination destination, UpdateDestinationRequestDTO dto, Country country) {
        destination.setName(dto.getName());
        destination.setDescription(dto.getDescription());
        destination.setImageUrl(dto.getImageUrl());
        destination.setCountry(country);
    }
}
