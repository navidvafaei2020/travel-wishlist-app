package lu.travelwishlist.app.service.wishlist;

import lu.travelwishlist.app.dto.response.WishlistResponseDTO;

import java.util.List;

public interface WishlistService {

    List<WishlistResponseDTO> getUserWishlist(Long userId);

    WishlistResponseDTO addDestinationToWishlist(Long userId, Long destinationId);

    void removeDestinationFromWishlist(Long userId, Long destinationId);

}
