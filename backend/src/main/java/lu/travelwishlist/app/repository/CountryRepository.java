package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Country entities.
 *
 * <p>
 * Provides methods to query countries by name.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    /** Finds countries with the given name. */
    List<Country> findByName(String name);
}
