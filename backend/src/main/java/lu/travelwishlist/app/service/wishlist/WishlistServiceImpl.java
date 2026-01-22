package lu.travelwishlist.app.service.wishlist;

import lu.travelwishlist.app.dto.response.WishlistResponseDTO;
import lu.travelwishlist.app.entity.*;
import lu.travelwishlist.app.mapper.WishlistMapper;
import lu.travelwishlist.app.repository.DestinationRepository;
import lu.travelwishlist.app.repository.DestinationTagRepository;
import lu.travelwishlist.app.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing user wishlists.
 * Provides methods to add, remove, and fetch destinations in a user's wishlist.
 */
@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final DestinationRepository destinationRepository;
    private final DestinationTagRepository destinationTagRepository;


    /**
     * Constructor to initialize repositories.
     *
     * @param wishlistRepository Repository for Wishlist entities.
     * @param destinationRepository Repository for Destination entities.
     * @param destinationTagRepository Repository for DestinationTag entities.
     */
    public WishlistServiceImpl(
            WishlistRepository wishlistRepository,
            DestinationRepository destinationRepository,
            DestinationTagRepository destinationTagRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.destinationRepository = destinationRepository;
        this.destinationTagRepository = destinationTagRepository;
    }


    /**
     * Retrieves the wishlist for a specific user.
     * Each destination is mapped to a response DTO including its tags.
     *
     * @param userId ID of the user.
     * @return List of WishlistResponseDTO.
     */
    @Override
    public List<WishlistResponseDTO> getUserWishlist(Long userId) {
        // Fetch all wishlist entries for the user
        List<Wishlist> wishlists = wishlistRepository.findByUserOrderByDestinationNameAsc(
                new User() {{ setId(userId); }}
        );

        return wishlists.stream()
                .map(w -> mapToDtoWithTags(w))
                .collect(Collectors.toList());
    }


    /**
     * Adds a destination to a user's wishlist.
     * Prevents duplicate entries.
     *
     * @param userId ID of the user.
     * @param destinationId ID of the destination.
     * @return WishlistResponseDTO representing the added destination.
     * @throws RuntimeException if destination not found or already in wishlist.
     */
    @Override
    public WishlistResponseDTO addDestinationToWishlist(Long userId, Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        User user = new User();
        user.setId(userId);

        // Prevent duplicates
        boolean exists = wishlistRepository.findByUserOrderByDestinationNameAsc(user).stream()
                .anyMatch(w -> w.getDestination().getId().equals(destinationId));

        if (exists) {
            throw new RuntimeException("Destination already in wishlist");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setDestination(destination);

        wishlistRepository.save(wishlist);
        return mapToDtoWithTags(wishlist);
    }


    /**
     * Removes a destination from a user's wishlist.
     *
     * @param userId ID of the user.
     * @param destinationId ID of the destination to remove.
     * @throws RuntimeException if destination is not in the wishlist.
     */
    @Override
    public void removeDestinationFromWishlist(Long userId, Long destinationId) {
        User user = new User();
        user.setId(userId);

        Wishlist wishlist = wishlistRepository.findByUserOrderByDestinationNameAsc(user).stream()
                .filter(w -> w.getDestination().getId().equals(destinationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Destination not in wishlist"));

        wishlistRepository.delete(wishlist);
    }


    /**
     * Helper method to map a Wishlist entity to a DTO including destination tags.
     *
     * @param wishlist Wishlist entity.
     * @return WishlistResponseDTO containing destination info and tags.
     */
    private WishlistResponseDTO mapToDtoWithTags(Wishlist wishlist) {
        Destination destination = wishlist.getDestination();

        // Fetch tags via DestinationTagRepository
        List<Tag> tags = destinationTagRepository.findByDestination(destination).stream()
                .sorted((dt1, dt2) -> dt1.getTag().getName().compareTo(dt2.getTag().getName()))
                .map(DestinationTag::getTag)
                .collect(Collectors.toList());

        return WishlistMapper.toDto(wishlist, tags);
    }
}
