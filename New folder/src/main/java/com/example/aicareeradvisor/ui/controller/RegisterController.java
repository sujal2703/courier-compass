package com.example.aicareeradvisor.ui.controller;

import com.example.aicareeradvisor.controller.UserController;
import com.example.aicareeradvisor.ui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backToLoginButton;

    private UserController userController;
    private MainApp mainApp;

    @FXML
    private void initialize() {
        // Set up event handlers
        registerButton.setOnAction(event -> handleRegister());
        backToLoginButton.setOnAction(event -> handleBackToLogin());

        // Set up validation listeners
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input
        if (!validateRegistrationInput(username, email, password, confirmPassword)) {
            return;
        }

        // Attempt registration
        boolean success = userController.registerUser(username, password, email);

        if (success) {
            showAlert(AlertType.INFORMATION, "Success", "Registration successful! You can now log in.");
            handleBackToLogin();
        } else {
            showAlert(AlertType.ERROR, "Registration Failed", "Registration failed. Username or email may already exist.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            // Close registration window and return to login
            closeRegistrationWindow();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error returning to login: " + e.getMessage());
        }
    }

    private boolean validateRegistrationInput(String username, String email, String password, String confirmPassword) {
        // Check for empty fields
        if (username.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Username is required.");
            return false;
        }

        if (email.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Email is required.");
            return false;
        }

        if (password.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Password is required.");
            return false;
        }

        if (confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Validation Error", "Please confirm your password.");
            return false;
        }

        // Validate username
        if (!userController.isValidUsername(username)) {
            showAlert(AlertType.ERROR, "Validation Error", "Username must be between 3 and 20 characters.");
            return false;
        }

        if (!userController.isUsernameAvailable(username)) {
            showAlert(AlertType.ERROR, "Validation Error", "Username is already taken.");
            return false;
        }

        // Validate email
        if (!userController.isValidEmail(email)) {
            showAlert(AlertType.ERROR, "Validation Error", "Please enter a valid email address.");
            return false;
        }

        if (!userController.isEmailAvailable(email)) {
            showAlert(AlertType.ERROR, "Validation Error", "Email is already registered.");
            return false;
        }

        // Validate password
        if (!userController.isValidPassword(password)) {
            showAlert(AlertType.ERROR, "Validation Error", "Password must be at least 6 characters long.");
            return false;
        }

        // Check password confirmation
        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Validation Error", "Passwords do not match.");
            return false;
        }

        return true;
    }

    private void validateForm() {
        // Enable/disable register button based on form validity
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        boolean isValid = !username.isEmpty() &&
                         !email.isEmpty() &&
                         !password.isEmpty() &&
                         !confirmPassword.isEmpty() &&
                         password.equals(confirmPassword);

        registerButton.setDisable(!isValid);
    }

    private void closeRegistrationWindow() {
        if (usernameField.getScene() != null && usernameField.getScene().getWindow() != null) {
            usernameField.getScene().getWindow().hide();
        }
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Dependency injection methods
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
