package co.duvan.security.app.service;

import co.duvan.security.app.http.AuthResponse;
import co.duvan.security.app.http.LoginRequest;
import co.duvan.security.app.http.RegisterRequest;
import co.duvan.security.app.model.Role;
import co.duvan.security.app.model.User;
import co.duvan.security.app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    //* Vars
    private final UserRepository userRepository;
    private final JwtService jwtService;

    //* Constructor
    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    //* Methods
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCountry(request.getCountry());
        user.setRole(Role.USER);

        userRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));

    }

}
