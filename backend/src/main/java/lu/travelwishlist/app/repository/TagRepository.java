package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Tag entities.
 *
 * <p>
 * Provides methods to query, check existence, and retrieve tags from the database.
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /** Finds tags with a given name (case-insensitive). */
    List<Tag> findByNameIgnoreCase(String name);

    /** Finds tags whose names are in the provided list (case-insensitive). */
    List<Tag> findByNameInIgnoreCase(List<String> names);

    /** Retrieves all tags ordered alphabetically by name. */
    List<Tag> findAllByOrderByNameAsc();

    /** Checks if a tag with the given name exists (case-insensitive). */
    boolean existsByNameIgnoreCase(String name);

    /** Checks if a tag with the given name exists, excluding a specific ID. */
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
