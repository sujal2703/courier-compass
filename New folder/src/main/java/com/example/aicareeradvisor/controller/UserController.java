package com.example.aicareeradvisor.controller;

import com.example.aicareeradvisor.entity.User;
import com.example.aicareeradvisor.service.UserService;
import java.util.List;
import java.util.Optional;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User registration
    public boolean registerUser(String username, String password, String email) {
        return userService.registerUser(username, password, email);
    }

    // User authentication
    public Optional<User> loginUser(String username, String password) {
        return userService.authenticateUser(username, password);
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userService.getUserById(id);
    }

    // Get user by username
    public Optional<User> getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Update user
    public boolean updateUser(User user) {
        return userService.updateUser(user);
    }

    // Delete user
    public boolean deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    // Search users
    public List<User> searchUsers(String username) {
        return userService.searchUsersByUsername(username);
    }

    // Validation methods
    public boolean isUsernameAvailable(String username) {
        return !userService.usernameExists(username);
    }

    public boolean isEmailAvailable(String email) {
        return !userService.emailExists(email);
    }

    public boolean isValidUsername(String username) {
        return username != null && username.trim().length() >= 3 && username.trim().length() <= 20;
    }

    public boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= 6;
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Simple email validation
        return email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf(".");
    }
}
