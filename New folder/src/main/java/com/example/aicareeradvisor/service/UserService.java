package com.example.aicareeradvisor.service;

import com.example.aicareeradvisor.entity.User;
import com.example.aicareeradvisor.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // User registration
    public boolean registerUser(String username, String password, String email) {
        // Validate input
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Check if user already exists
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            return false;
        }
        
        // Hash password
        String hashedPassword = hashPassword(password);
        
        // Create and save user
        User user = new User(username, hashedPassword, email);
        userRepository.save(user);
        return true;
    }
    
    // User authentication
    public Optional<User> authenticateUser(String username, String password) {
        if (username == null || password == null) {
            return Optional.empty();
        }
        
        String hashedPassword = hashPassword(password);
        return userRepository.findByUsernameAndPassword(username, hashedPassword);
    }
    
    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    // Get user by username
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Update user
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            user.setUpdatedAt(java.time.LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    // Delete user
    public boolean deleteUser(Long id) {
        if (id == null) {
            return false;
        }
        
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Search users by username
    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContaining(username);
    }
    
    // Check if username exists
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    // Check if email exists
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
