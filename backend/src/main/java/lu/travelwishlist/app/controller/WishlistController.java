package lu.travelwishlist.app.controller;


import lu.travelwishlist.app.dto.response.WishlistResponseDTO;
import lu.travelwishlist.app.service.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<WishlistResponseDTO>> getUserWishlist(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{destinationId}")
    public ResponseEntity<WishlistResponseDTO> addToWishlist(@AuthenticationPrincipal Long userId,
                                                             @PathVariable Long destinationId) {
        return ResponseEntity.ok(wishlistService.addDestinationToWishlist(userId, destinationId));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{destinationId}")
    public ResponseEntity<Void> removeFromWishlist(@AuthenticationPrincipal Long userId,
                                                   @PathVariable Long destinationId) {
        wishlistService.removeDestinationFromWishlist(userId, destinationId);
        return ResponseEntity.noContent().build();
    }
}
