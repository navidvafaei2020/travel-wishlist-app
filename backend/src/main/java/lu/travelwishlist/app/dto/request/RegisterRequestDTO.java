package lu.travelwishlist.app.dto.request;

import jakarta.validation.constraints.*;
import lu.travelwishlist.app.entity.UserRole;

/**
 * Data Transfer Object for user registration requests.
 *
 * <p>
 * Contains fields for username, email, password, first and last name, and user role.
 * Validation annotations ensure required fields and format constraints.
 */
public class RegisterRequestDTO {

    /** Username of the user. Must be 3-50 chars, letters/numbers/hyphen/underscore only */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
            message = "Username can only contain letters, numbers, hyphens and underscores")
    private String username;

    /** User's email address, must be valid format */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    /** User's password, minimum 8 characters */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    /** Optional first name, maximum 100 characters */
    @Size(max = 100, message = "First name can br maximum 100 characters")
    private String firstName;

    /** Optional last name, maximum 100 characters */
    @Size(max = 100, message = "Last name can be maximum 100 characters")
    private String lastName;

    /** User role, must not be null */
    @NotNull(message = "Role is required")
    private UserRole role;



    // Constructors, Getters and setters
    public RegisterRequestDTO(){}

    public RegisterRequestDTO(String username, String email, String password, String firstName, String lastName, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }

}
