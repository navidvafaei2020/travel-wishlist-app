package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.response.AuthResponseDTO;
import lu.travelwishlist.app.entity.User;

public class AuthMapper {

    public static AuthResponseDTO toDto(User user, String token) {
        return new AuthResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                token,
                user.getStatus()
        );
    }
}
