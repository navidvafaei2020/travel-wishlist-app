package lu.travelwishlist.app.service.country;

import lu.travelwishlist.app.entity.Country;

import java.util.List;

/**
 * Service interface for managing countries.
 *
 * <p>
 * Provides methods to retrieve all countries or a specific country by ID.
 */
public interface CountryService {
    /**
     * Retrieves all countries.
     *
     * @return list of all Country entities
     */
    List<Country> getAllCountries();


    /**
     * Retrieves a specific country by its ID.
     *
     * @param id the country ID
     * @return the Country entity
     */
    Country getCountryById(Long id);
}
