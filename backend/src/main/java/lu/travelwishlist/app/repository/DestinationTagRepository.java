package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.DestinationTag;
import lu.travelwishlist.app.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for DestinationTag entities.
 *
 * <p>
 * Provides methods to query, check existence, and delete associations
 * between destinations and tags.
 */
@Repository
public interface DestinationTagRepository extends JpaRepository<DestinationTag, Long> {

    /** Finds all DestinationTag entries for a given destination. */
    List<DestinationTag> findByDestination(Destination destination);

    /** Finds all DestinationTag entries for a given tag, ordered by destination name. */
    List<DestinationTag> findByTagOrderByDestinationNameAsc(Tag tag);

    /** Deletes all DestinationTag entries for a given destination. */
    void deleteByDestination(Destination destination);

    /** Checks if a DestinationTag association exists between a specific destination and tag. */
    boolean existsByDestinationAndTag(Destination destination, Tag tag);
}
