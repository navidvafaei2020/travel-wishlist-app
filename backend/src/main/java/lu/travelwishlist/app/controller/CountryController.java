package lu.travelwishlist.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.service.country.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * REST controller for managing countries.
 *
 * <p>
 * Provides endpoints for retrieving all countries or a specific country by ID.
 * Typically used for dropdowns or reference data in the application.
 */
@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;


    /**
     * Retrieves all available countries.
     *
     * @return list of {@link Country}
     */
    @Operation(
            summary = "Get all countries",
            description = "Returns a list of all available countries. Used for dropdown selection."
    )
    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }


    /**
     * Retrieves a specific country by its ID.
     *
     * @param id country ID
     * @return {@link Country} for the given ID
     */
    @Operation(
            summary = "Get country by ID",
            description = "Returns details of a specific country by its ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountry(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getCountryById(id));
    }
}