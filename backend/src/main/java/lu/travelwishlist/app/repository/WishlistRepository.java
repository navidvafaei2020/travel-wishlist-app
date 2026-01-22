package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.User;
import lu.travelwishlist.app.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Wishlist entities.
 *
 * <p>
 * Provides methods to query wishlist entries by user or destination.
 */
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * Finds all wishlist entries of a given user, ordered by destination name ascending.
     */
    List<Wishlist> findByUserOrderByDestinationNameAsc(User user);


    /**
     * Finds all wishlist entries for a specific destination.
     */
    List<Wishlist> findByDestination(Destination destination);
}