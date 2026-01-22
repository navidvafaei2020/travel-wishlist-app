package lu.travelwishlist.app.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user login requests.
 *
 * <p>
 * Contains username and password fields with validation to ensure they are provided.
 */
public class LoginRequestDTO {

    /** Username of the user. Required. */
    @NotBlank(message = "Username is required")
    private String username;

    /** Password of the user. Required. */
    @NotBlank(message = "Password is required")
    private String password;



    // Constructors, getters, setters...

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
