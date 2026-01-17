package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.User;
import lu.travelwishlist.app.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    // Find all wishlist entries of a user, ordered by the destination name ascending
    List<Wishlist> findByUserOrderByDestinationNameAsc(User user);

}
