package lu.travelwishlist.app.dto.response;


import lu.travelwishlist.app.entity.UserRole;
import lu.travelwishlist.app.entity.UserStatus;

/**
 * Data Transfer Object for returning user details in responses.
 *
 * <p>
 * Encapsulates basic user information including ID, username, email, name,
 * role, and account status.
 */
public class UserResponseDTO {

    /** User ID */
    private Long id;

    /** Username */
    private String username;

    /** Email address */
    private String email;

    /** First name */
    private String firstName;

    /** Last name */
    private String lastName;

    /** User role (e.g., ADMIN, USER) */
    private UserRole role;

    /** Account status (e.g., ACTIVE, INACTIVE) */
    private UserStatus status;



    // Constructors, getters, setters, ...
    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String username, String email, String firstName, String lastName, UserRole role, UserStatus status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }

}
