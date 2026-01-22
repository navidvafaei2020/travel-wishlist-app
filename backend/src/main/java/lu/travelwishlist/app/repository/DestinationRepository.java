package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Destination entities.
 *
 * <p>
 * Provides methods to query destinations by name, country, and check for existence.
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {


    /** Finds destinations with a given name (case-insensitive). */
    List<Destination> findByNameIgnoreCase(String name);

    /** Finds all destinations belonging to a specific country. */
    List<Destination> findByCountry(Country country);

    /** Finds destinations with a given name in a specific country. */
    List<Destination> findByNameIgnoreCaseAndCountry(String name, Country country);

    /** Retrieves destinations for a country ordered alphabetically by name. */
    List<Destination> findByCountryOrderByNameAsc(Country country);

    /** Checks if a destination exists with the given name in a specific country. */
    boolean existsByNameIgnoreCaseAndCountry(String name, Country country);

    /** Checks if a destination exists with the given name in a country, excluding a specific ID. */
    boolean existsByNameIgnoreCaseAndCountryAndIdNot(String name, Country country, Long id);
}
