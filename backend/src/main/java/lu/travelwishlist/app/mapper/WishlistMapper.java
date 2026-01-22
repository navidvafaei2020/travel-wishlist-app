package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.response.WishlistResponseDTO;
import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.Tag;
import lu.travelwishlist.app.entity.Wishlist;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Wishlist entities and DTOs.
 *
 * <p>
 * Provides methods for entity → DTO conversion. DTO → entity conversion
 * is not required because wishlist entries are created from the authenticated user
 * and destination ID.
 */
public class WishlistMapper {

    /**
     * Converts a Wishlist entity and its associated tags to a WishlistResponseDTO.
     *
     * @param wishlist the Wishlist entity
     * @param tags the list of Tag entities associated with the destination
     * @return a WishlistResponseDTO containing wishlist and destination details
     */
    public static WishlistResponseDTO toDto(Wishlist wishlist, List<Tag> tags) {
        Destination d = wishlist.getDestination();
        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        return new WishlistResponseDTO(
                wishlist.getId(),
                d.getId(),
                d.getName(),
                d.getDescription(),
                d.getImageUrl(),
                d.getCountry().getId(),
                d.getCountry().getName(),
                tagNames
        );
    }

    // DTO → Entity is not needed because wishlist is created from authenticated user + destinationId
}
