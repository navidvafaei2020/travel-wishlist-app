package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.request.RegisterRequestDTO;
import lu.travelwishlist.app.dto.response.UserResponseDTO;
import lu.travelwishlist.app.entity.User;
import lu.travelwishlist.app.entity.UserStatus;

public class UserMapper {

    // Entity → DTO
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


    // DTO → Entity (for creation, e.g., RegisterRequestDTO)
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

    // DTO → Entity (optional, e.g., for admin updates)
    public static void updateStatus(User user, UserStatus status) {
        user.setStatus(status);
    }

}
