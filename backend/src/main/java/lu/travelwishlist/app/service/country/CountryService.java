package lu.travelwishlist.app.service.country;

import lu.travelwishlist.app.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();

    Country getCountryById(Long id);
}
