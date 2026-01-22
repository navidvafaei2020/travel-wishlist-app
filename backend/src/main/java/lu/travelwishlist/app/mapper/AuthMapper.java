package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.response.AuthResponseDTO;
import lu.travelwishlist.app.entity.User;

/**
 * Mapper class for converting User entities to authentication response DTOs.
 *
 * <p>
 * Provides methods to build AuthResponseDTOs that include user details and JWT token.
 */
public class AuthMapper {

    /**
     * Converts a User entity and JWT token into an AuthResponseDTO.
     *
     * @param user the User entity
     * @param token the JWT token assigned to the user
     * @return an AuthResponseDTO containing user info and token
     */
    public static AuthResponseDTO toDto(User user, String token) {
        return new AuthResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                token,
                user.getStatus()
        );
    }
}
