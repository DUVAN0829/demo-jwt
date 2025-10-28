package co.duvan.security.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //* Methods handler
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Logged in");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.status(HttpStatus.CREATED).body("Registered");
    }

}
