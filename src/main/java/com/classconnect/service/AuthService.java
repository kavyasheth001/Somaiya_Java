package com.classconnect.service;

import com.classconnect.dto.AuthRequest;
import com.classconnect.dto.RegisterRequest;
import com.classconnect.dto.UserResponse;
import com.classconnect.entity.User;
import com.classconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());
        
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }
    
    public UserResponse login(AuthRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }
        
        User user = userOptional.get();
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        return new UserResponse(user);
    }
    
    public UserResponse getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(java.util.UUID.fromString(userId));
        
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return new UserResponse(userOptional.get());
    }
    
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
    
    public List<UserResponse> getAllUsersByRole(User.Role role) {
        return userRepository.findByRole(role).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
}

