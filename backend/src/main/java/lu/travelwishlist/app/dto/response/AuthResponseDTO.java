package lu.travelwishlist.app.dto.response;

import lu.travelwishlist.app.entity.UserStatus;

/**
 * Data Transfer Object for authentication responses.
 *
 * <p>
 * Encapsulates user details along with the JWT token returned upon login or registration.
 */
public class AuthResponseDTO {

    /** User ID */
    private Long userId;

    /** Username of the user */
    private String username;

    /** Email of the user */
    private String email;

    /** First name of the user */
    private String firstName;

    /** Last name of the user */
    private String lastName;

    /** Role of the user (e.g., ADMIN, USER) */
    private String role;

    /** JWT token for authentication */
    private String token;

    /** Token type, default "Bearer" */
    private String type = "Bearer";

    /** Status of the user account */
    private UserStatus status;




    // Constructors, getters, setters, ...
    public AuthResponseDTO() {}

    public AuthResponseDTO(Long userId, String username, String email, String firstName, String lastName, String role, String token, UserStatus status) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.token = token;
        this.status = status;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
}
