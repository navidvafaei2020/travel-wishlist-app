package lu.travelwishlist.app.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lu.travelwishlist.app.dto.response.WishlistResponseDTO;
import lu.travelwishlist.app.service.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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


    @Operation(
            summary = "Get user's wishlist",
            description = "Returns all destinations saved in the authenticated user's wishlist.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<WishlistResponseDTO>> getUserWishlist(
            @AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }


    @Operation(
            summary = "Add destination to wishlist",
            description = "Adds a destination to the authenticated user's wishlist.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{destinationId}")
    public ResponseEntity<WishlistResponseDTO> addToWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long destinationId) {

        WishlistResponseDTO created =
                wishlistService.addDestinationToWishlist(userId, destinationId);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @Operation(
            summary = "Remove destination from wishlist",
            description = "Removes a destination from the authenticated user's wishlist.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{destinationId}")
    public ResponseEntity<Void> removeFromWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long destinationId) {
        wishlistService.removeDestinationFromWishlist(userId, destinationId);
        return ResponseEntity.noContent().build();
    }
}
