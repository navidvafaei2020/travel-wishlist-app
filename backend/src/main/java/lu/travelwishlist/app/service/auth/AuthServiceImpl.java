package lu.travelwishlist.app.service.auth;


import lu.travelwishlist.app.dto.request.LoginRequestDTO;
import lu.travelwishlist.app.dto.request.RegisterRequestDTO;
import lu.travelwishlist.app.dto.response.AuthResponseDTO;
import lu.travelwishlist.app.entity.User;
import lu.travelwishlist.app.exception.EmailAlreadyExistsException;
import lu.travelwishlist.app.exception.InvalidCredentialsException;
import lu.travelwishlist.app.exception.UsernameAlreadyExistsException;
import lu.travelwishlist.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponseDTO register(RegisterRequestDTO dto) {
        // 1. Check if username already exists
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException(dto.getUsername());

        }

        // 2. Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }

        // 3. Create new user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // We should hash the password
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());


        // 4. Save user
        User savedUser = userRepository.save(user);

        // 5. Generate JWT token
        String token = jwtService.generateToken(savedUser);

        // 7. Return response
        return new AuthResponseDTO(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getRole().toString(),
                token,
                savedUser.getStatus()
        );

    }


    public AuthResponseDTO login(LoginRequestDTO dto) {
        // 1. Find user by username
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());

        // 2. Check password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // 3. Generate JWT token
        String token = jwtService.generateToken(user);

        // 4. Return response
        return new AuthResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().toString(),
                token,
                user.getStatus()
        );
    }
}