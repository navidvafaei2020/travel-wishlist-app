package lu.travelwishlist.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing a Tag.
 *
 * <p>
 * Tags are labels that can be assigned to destinations (e.g., "Beach", "Mountain", "Cheap").
 * Each tag may be associated with multiple destinations via the DestinationTag join entity.
 */
@Entity
@Table(name = "tags")
public class Tag {

    /** Primary key of the tag */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the tag. Must be unique and not null */
    @Column(nullable = false, unique = true)
    private String name;

    /** List of associations with destinations. Not serialized in JSON */
    @JsonIgnore
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DestinationTag> destinationTags = new ArrayList<>();


    // Empty constructor (required by JPA)
    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<DestinationTag> getDestinationTags() { return destinationTags; }
    public void setDestinationTags(List<DestinationTag> destinationTags) { this.destinationTags = destinationTags; }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
