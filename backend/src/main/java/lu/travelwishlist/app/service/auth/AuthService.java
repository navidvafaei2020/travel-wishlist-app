package lu.travelwishlist.app.service.auth;

import lu.travelwishlist.app.dto.request.LoginRequestDTO;
import lu.travelwishlist.app.dto.request.RegisterRequestDTO;
import lu.travelwishlist.app.dto.response.AuthResponseDTO;

/**
 * Service interface for authentication operations.
 *
 * <p>
 * Provides methods for user login and registration, returning
 * user details along with a JWT token.
 */
public interface AuthService {

    /**
     * Authenticates a user and returns a JWT token with user details.
     *
     * @param loginRequest the login request DTO containing username and password
     * @return AuthResponseDTO containing user info and JWT token
     */
    AuthResponseDTO login(LoginRequestDTO loginRequest);


    /**
     * Registers a new user and returns a JWT token with user details.
     *
     * @param registerRequest the registration request DTO
     * @return AuthResponseDTO containing user info and JWT token
     */
    AuthResponseDTO register(RegisterRequestDTO registerRequest);
}
