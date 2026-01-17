package lu.travelwishlist.app.service.auth;

import lu.travelwishlist.app.dto.request.LoginRequestDTO;
import lu.travelwishlist.app.dto.request.RegisterRequestDTO;
import lu.travelwishlist.app.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO loginRequest);

    AuthResponseDTO register(RegisterRequestDTO registerRequest);

}
