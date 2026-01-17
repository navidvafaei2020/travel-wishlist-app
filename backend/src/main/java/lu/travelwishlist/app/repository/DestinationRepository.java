package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Country;
import lu.travelwishlist.app.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    List<Destination> findByNameIgnoreCase(String name);
    List<Destination> findByCountry(Country country);
    List<Destination> findByNameIgnoreCaseAndCountry(String name, Country country);
    List<Destination> findByCountryOrderByNameAsc(Country country);

}
