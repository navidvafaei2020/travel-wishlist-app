package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.request.RegisterRequestDTO;
import lu.travelwishlist.app.dto.response.UserResponseDTO;
import lu.travelwishlist.app.entity.User;
import lu.travelwishlist.app.entity.UserStatus;

/**
 * Mapper class for converting between User entities and DTOs.
 *
 * <p>
 * Provides utility methods for entity → DTO conversion, DTO → entity creation,
 * and updating specific fields like status.
 */
public class UserMapper {

    /**
     * Converts a User entity to a UserResponseDTO.
     *
     * @param user the User entity
     * @return a UserResponseDTO with user details, or null if user is null
     */
    public static UserResponseDTO toDto(User user) {

        if (user == null) {
            return null;
        }

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getStatus()
        );
    }


    /**
     * Converts a RegisterRequestDTO to a new User entity.
     *
     * @param dto the registration request DTO
     * @return a new User entity, or null if dto is null
     */
    public static User toEntity(RegisterRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }


    /**
     * Updates the status of an existing User entity.
     *
     * @param user the user entity to update
     * @param status the new status
     */
    public static void updateStatus(User user, UserStatus status) {
        user.setStatus(status);
    }
}
