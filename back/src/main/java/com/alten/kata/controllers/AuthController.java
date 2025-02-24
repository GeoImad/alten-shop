package com.alten.kata.controllers;

import com.alten.kata.component.JwtUtil;
import com.alten.kata.model.User;
import com.alten.kata.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/account")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(authService.findAllUsers());
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        Optional<User> user = authService.findByEmail(credentials.get("email"));

        if (user.isPresent() && passwordEncoder.matches(credentials.get("password"), user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }
}
