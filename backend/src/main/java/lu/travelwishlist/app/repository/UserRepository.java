package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 *
 * <p>
 * Provides methods to query users by username or email and check for existence.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /** Finds a user by username. */
    Optional<User> findByUsername(String username);

    /** Finds a user by email. */
    Optional<User> findByEmail(String email);

    /** Checks if a user exists with the given username. */
    boolean existsByUsername(String username);

    /** Checks if a user exists with the given email. */
    boolean existsByEmail(String email);
}
