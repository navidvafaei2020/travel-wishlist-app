package lu.travelwishlist.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity representing an application User.
 *
 * <p>
 * Users have unique usernames and emails, a password, personal details, role, and status.
 * Each user can have multiple wishlist entries linking them to destinations.
 */
@Entity
@Table(name = "users")
public class User {

    /** Primary key of the user */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Username of the user. Must be unique and not null */
    @Column(nullable = false, unique = true)
    private String username;

    /** Email of the user. Must be unique and not null */
    @Column(nullable = false, unique = true)
    private String email;

    /** Encrypted password of the user. Required */
    @Column(nullable = false)
    private String password;

    /** First name of the user. Required */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** Last name of the user. Required */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** Role of the user (ADMIN, USER) */
    @Enumerated(EnumType.STRING)
    private UserRole role;

    /** Status of the user account (ACTIVE, INACTIVE, etc.) */
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    /** Wishlist entries associated with this user. Not serialized in JSON */
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wishlist> wishlists = new ArrayList<>();



    // Constructors, getters, setters...

    // Empty constructor (required by JPA)
    // Initialize Default Role & Status, this prevents null values without extra logic
    public User() {
        this.role = UserRole.USER;
        this.status = UserStatus.ACTIVE;
    }

    public User(String username, String email, String password, String firstName, String lastName, UserRole role, UserStatus status, List<Wishlist> wishlists) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
        this.wishlists = wishlists;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

    public List<Wishlist> getWishlists() { return wishlists; }
    public void setWishlists(List<Wishlist> wishlists) { this.wishlists = wishlists; }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", username='" + getUsername() + "'" +
                ", email='" + getEmail() + "'" +
                ", password='" + getPassword() + "'" +
                ", role='" + getRole() + "'" +
                ", status='" + getStatus() + "'" +
                ", wishlistings='" + getWishlists() + "'" +
                "}";
    }
}
