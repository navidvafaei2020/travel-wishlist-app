package lu.travelwishlist.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a Country.
 *
 * <p>
 * Each country has a unique name and can be associated with multiple destinations.
 */
@Entity
@Table(name = "countries")
public class Country {

    /** Primary key of the country */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the country. Must be unique and not null */
    @Column(nullable = false, unique = true)
    private String name;

    /** List of destinations belonging to this country. Not serialized in JSON */
    @JsonIgnore
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destination> destinations = new ArrayList<>();



    // Empty constructor (required by JPA)
    public Country() {}

    public Country(String name, List<Destination> destinations) {
        this.name = name;
        this.destinations = destinations;
    }


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", name='" + getName() + "'" +
                "}";
    }
}
