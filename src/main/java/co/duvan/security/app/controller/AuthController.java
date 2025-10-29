package co.duvan.security.app.controller;

import co.duvan.security.app.http.AuthResponse;
import co.duvan.security.app.http.LoginRequest;
import co.duvan.security.app.http.RegisterRequest;
import co.duvan.security.app.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //* Vars
    private final AuthService authService;

    //* Constructor
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //* Methods handler
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));

    }

}
