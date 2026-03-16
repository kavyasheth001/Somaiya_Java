package com.classconnect.controller;

import com.classconnect.dto.AuthRequest;
import com.classconnect.dto.RegisterRequest;
import com.classconnect.dto.UserResponse;
import com.classconnect.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.classconnect.entity.User;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserResponse user = authService.register(request);
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "user", user
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {
            UserResponse user = authService.login(request);
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "user", user,
                "token", "mock-jwt-token-" + user.getId() // Mock token for now
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String role) {
        try {
            if (role != null) {
                User.Role userRole = User.Role.valueOf(role.toUpperCase());
                var users = authService.getAllUsersByRole(userRole);
                return ResponseEntity.ok(users);
            } else {
                var users = authService.getAllUsers();
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        try {
            UserResponse user = authService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

