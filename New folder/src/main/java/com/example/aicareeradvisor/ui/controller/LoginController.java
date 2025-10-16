package com.example.aicareeradvisor.ui.controller;

import com.example.aicareeradvisor.controller.UserController;
import com.example.aicareeradvisor.entity.User;
import com.example.aicareeradvisor.ui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private UserController userController;
    private MainApp mainApp;

    @FXML
    private void initialize() {
        // Set up event handlers
        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());

        // Enable/disable login button based on field content
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateLoginButtonState();
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateLoginButtonState();
        });

        // Initial state
        updateLoginButtonState();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Please enter both username and password.");
            return;
        }

        // Attempt authentication
        Optional<User> user = userController.loginUser(username, password);

        if (user.isPresent()) {
            System.out.println("Login successful for user: " + user.get().getUsername());
            try {
                // Close login window and open dashboard
                System.out.println("Attempting to show dashboard...");
                mainApp.showDashboardView(user.get());
                System.out.println("Dashboard shown, closing login window...");
                closeLoginWindow();
                System.out.println("Login process completed successfully");
            } catch (IOException e) {
                System.err.println("Error opening dashboard: " + e.getMessage());
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Navigation Error", "Could not open dashboard: " + e.getMessage());
            }
        } else {
            System.out.println("Login failed - invalid credentials");
            showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
            passwordField.clear();
        }
    }

    @FXML
    private void handleRegister() {
        try {
            mainApp.showRegisterView();
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Navigation Error", "Could not open registration window: " + e.getMessage());
        }
    }

    private void updateLoginButtonState() {
        boolean hasUsername = !usernameField.getText().trim().isEmpty();
        boolean hasPassword = !passwordField.getText().isEmpty();
        loginButton.setDisable(!hasUsername || !hasPassword);
    }

    private void closeLoginWindow() {
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
