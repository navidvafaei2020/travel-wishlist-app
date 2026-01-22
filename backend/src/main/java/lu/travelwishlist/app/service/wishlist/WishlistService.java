package lu.travelwishlist.app.service.wishlist;

import lu.travelwishlist.app.dto.response.WishlistResponseDTO;

import java.util.List;

/**
 * Service interface for managing user wishlists.
 * Defines methods to retrieve, add, and remove destinations in a user's wishlist.
 */
public interface WishlistService {

    /**
     * Retrieves the wishlist for a specific user.
     * Each destination is represented as a DTO including its tags.
     *
     * @param userId ID of the user.
     * @return List of WishlistResponseDTO for the user's wishlist.
     */
    List<WishlistResponseDTO> getUserWishlist(Long userId);

    /**
     * Adds a destination to a user's wishlist.
     * Prevents adding duplicates.
     *
     * @param userId ID of the user.
     * @param destinationId ID of the destination to add.
     * @return WishlistResponseDTO representing the added destination.
     * @throws RuntimeException if the destination does not exist or is already in the wishlist.
     */
    WishlistResponseDTO addDestinationToWishlist(Long userId, Long destinationId);

    /**
     * Removes a destination from a user's wishlist.
     *
     * @param userId ID of the user.
     * @param destinationId ID of the destination to remove.
     * @throws RuntimeException if the destination is not found in the wishlist.
     */
    void removeDestinationFromWishlist(Long userId, Long destinationId);
}
