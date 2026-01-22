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

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final DestinationRepository destinationRepository;
    private final DestinationTagRepository destinationTagRepository;

    public WishlistServiceImpl(
            WishlistRepository wishlistRepository,
            DestinationRepository destinationRepository,
            DestinationTagRepository destinationTagRepository
    ) {
        this.wishlistRepository = wishlistRepository;
        this.destinationRepository = destinationRepository;
        this.destinationTagRepository = destinationTagRepository;
    }


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


    // Helper to map Wishlist → DTO
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
