package lu.travelwishlist.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many wishlists → One User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Many wishlists → One Destination
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