package lu.travelwishlist.app.entity;

import jakarta.persistence.*;

/**
 * JPA entity representing a Wishlist entry.
 *
 * <p>
 * Each entry links a user to a destination, allowing users to save destinations to their wishlist.
 * Many wishlist entries can belong to the same user or destination.
 */
@Entity
@Table(name = "wishlists")
public class Wishlist {

    /** Primary key of the wishlist entry */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Associated user. Many wishlist entries → one user */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Associated destination. Many wishlist entries → one destination */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;



    // Empty constructor required by JPA
    public Wishlist() {}

    public Wishlist(User user, Destination destination) {
        this.user = user;
        this.destination = destination;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", user=" + (user != null ? user.getUsername() : "null") +
                ", destination=" + (destination != null ? destination.getName() : "null") +
                '}';
    }
}