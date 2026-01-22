package lu.travelwishlist.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lu.travelwishlist.app.dto.request.LoginRequestDTO;
import lu.travelwishlist.app.dto.request.RegisterRequestDTO;
import lu.travelwishlist.app.dto.response.AuthResponseDTO;
import lu.travelwishlist.app.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication and user registration.
 *
 * <p>
 * Provides endpoints for:
 * <ul>
 *     <li>User login and JWT token generation</li>
 *     <li>User registration with automatic JWT token generation</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    /**
     * Authenticates a user with username and password.
     *
     * @param loginRequest login credentials
     * @return {@link AuthResponseDTO} containing JWT token
     */
    @Operation(
            summary = "User login",
            description = "Authenticates a user using username and password and returns a JWT token."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }


    /**
     * Registers a new user.
     *
     * @param registerRequest registration details
     * @return {@link AuthResponseDTO} containing JWT token for the newly registered user
     */
    @Operation(
            summary = "User registration",
            description = "Registers a new user and returns a JWT token."
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        AuthResponseDTO created = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
