package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;
import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class DestinationMapper {

    // Entity → DTO
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


    // DTO → Entity (for creation)
    public static Destination toEntity(CreateDestinationRequestDTO dto, Country country) {
        Destination destination = new Destination();
        destination.setName(dto.getName());
        destination.setDescription(dto.getDescription());
        destination.setImageUrl(dto.getImageUrl());
        destination.setCountry(country);
        return destination;
    }


    // Update existing entity from DTO
    public static void updateEntity(Destination destination, UpdateDestinationRequestDTO dto, Country country) {
        destination.setName(dto.getName());
        destination.setDescription(dto.getDescription());
        destination.setImageUrl(dto.getImageUrl());
        destination.setCountry(country);
    }
}
