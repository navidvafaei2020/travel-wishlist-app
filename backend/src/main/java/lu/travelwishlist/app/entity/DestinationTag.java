package lu.travelwishlist.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination_tags")
public class DestinationTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many destination-tags → One Destination
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    // Many destination-tags → One Tag
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;



    // Empty constructor (required by JPA)
    public DestinationTag() {}

    public DestinationTag(Destination destination, Tag tag) {
        this.destination = destination;
        this.tag = tag;
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }

    public Tag getTag() { return tag; }
    public void setTag(Tag tag) { this.tag = tag; }

    @Override
    public String toString() {
        return "DestinationTag{" +
                "id=" + id +
                ", destination=" + (destination != null ? destination.getName() : "null") +
                ", tag=" + (tag != null ? tag.getName() : "null") +
                '}';
    }


}
