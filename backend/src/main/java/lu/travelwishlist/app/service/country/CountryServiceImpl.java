package lu.travelwishlist.app.service.country;

import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing countries.
 *
 * <p>
 * Provides methods to fetch all countries or a single country by ID.
 */
@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Retrieves all countries from the database.
     *
     * @return list of Country entities
     */
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }


    /**
     * Retrieves a country by its ID.
     *
     * @param id the country ID
     * @return the Country entity
     * @throws RuntimeException if country not found
     */
    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
    }
}
