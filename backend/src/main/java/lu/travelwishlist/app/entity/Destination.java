package lu.travelwishlist.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a Destination.
 *
 * <p>
 * Destinations are travel locations that belong to a country and can be associated
 * with multiple users' wishlists. They may have tags (via DestinationTag) and an image.
 */
@Entity
@Table(name = "destinations")
public class Destination {


    /** Primary key of the destination */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the destination. Required */
    @Column(nullable = false)
    private String name;

    /** Description of the destination. Optional */
    @Column
    private String description;

    /** Image URL of the destination. Optional */
    @Column
    private String imageUrl;

    /** Country this destination belongs to. Many destinations → one country */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /** Wishlist entries linking users to this destination. Not serialized in JSON */
    @JsonIgnore
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wishlist> wishlists = new ArrayList<>();



    // Empty constructor (required by JPA)
    public Destination() {}

    // Parameterized constructor
    public Destination(String name, String description, String imageUrl, Country country) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.country = country;
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public List<Wishlist> getWishlists() { return wishlists; }
    public void setWishlists(List<Wishlist> wishlists) { this.wishlists = wishlists; }


    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + (country != null ? country.getName() : "null") +
                '}';
    }
}
