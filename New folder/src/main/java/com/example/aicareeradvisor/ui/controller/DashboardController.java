package com.example.aicareeradvisor.ui.controller;

import com.example.aicareeradvisor.controller.CareerPathController;
import com.example.aicareeradvisor.controller.UserController;
import com.example.aicareeradvisor.entity.CareerPath;
import com.example.aicareeradvisor.entity.User;
import com.example.aicareeradvisor.ui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.ArrayList;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> educationComboBox;

    @FXML
    private ListView<CareerPath> careerPathsListView;

    @FXML
    private Button takeQuizButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button profileButton;

    @FXML
    private VBox careerDetailsVBox;

    @FXML
    private Label resultsCountLabel;

    private UserController userController;
    private CareerPathController careerPathController;
    private User currentUser;
    private MainApp mainApp;

    @FXML
    private void initialize() {
        System.out.println("DashboardController initialize() called");
        
        try {
            // Set up event handlers
            System.out.println("Setting up event handlers...");
            searchField.setOnKeyPressed(event -> handleSearch());
            categoryComboBox.setOnAction(event -> handleCategoryFilter());
            educationComboBox.setOnAction(event -> handleEducationFilter());
            takeQuizButton.setOnAction(event -> handleTakeQuiz());
            logoutButton.setOnAction(event -> handleLogout());
            profileButton.setOnAction(event -> handleProfile());

            // Set up list view selection listener
            careerPathsListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleCareerPathSelection(newValue));

            // Load initial data
            System.out.println("Loading initial data...");
            loadCareerPaths();
            loadCategories();
            loadEducationLevels();
            System.out.println("DashboardController initialization complete");
        } catch (Exception e) {
            System.err.println("Error in DashboardController initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            List<CareerPath> results = careerPathController.searchCareerPathsByName(searchTerm);
            displayCareerPaths(results);
        } else {
            loadCareerPaths(); // Show all career paths
        }
    }

    @FXML
    private void handleCategoryFilter() {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null && !selectedCategory.equals("All Categories")) {
            List<CareerPath> results = careerPathController.getCareerPathsByCategory(selectedCategory);
            displayCareerPaths(results);
        } else {
            loadCareerPaths(); // Show all career paths
        }
    }

    @FXML
    private void handleEducationFilter() {
        String selectedEducation = educationComboBox.getValue();
        if (selectedEducation != null && !selectedEducation.equals("All Education Levels")) {
            List<CareerPath> results = careerPathController.getCareerPathsByEducationLevel(selectedEducation);
            displayCareerPaths(results);
        } else {
            loadCareerPaths(); // Show all career paths
        }
    }

    @FXML
    private void handleTakeQuiz() {
        try {
            mainApp.showQuizView(currentUser);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error opening quiz: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogout() {
        // Clear current user and return to login
        currentUser = null;
        closeDashboardWindow();
        // Return to login screen
    }

    @FXML
    private void handleProfile() {
        // Open user profile window
        showAlert(Alert.AlertType.INFORMATION, "Profile", "Profile functionality coming soon!");
    }

    private void handleCareerPathSelection(CareerPath careerPath) {
        if (careerPath != null) {
            showCareerPathDetails(careerPath);
        }
    }

    private void loadCareerPaths() {
        System.out.println("Loading career paths...");
        try {
            if (careerPathController != null) {
                List<CareerPath> careerPaths = careerPathController.getAllCareerPaths();
                System.out.println("Found " + careerPaths.size() + " career paths");
                displayCareerPaths(careerPaths);
            } else {
                System.out.println("CareerPathController is null, loading empty list");
                displayCareerPaths(new ArrayList<>());
            }
        } catch (Exception e) {
            System.err.println("Error loading career paths: " + e.getMessage());
            e.printStackTrace();
            displayCareerPaths(new ArrayList<>());
        }
    }

    private void displayCareerPaths(List<CareerPath> careerPaths) {
        ObservableList<CareerPath> observableList = FXCollections.observableArrayList(careerPaths);
        careerPathsListView.setItems(observableList);
        resultsCountLabel.setText("Showing " + careerPaths.size() + " career paths");
    }

    private void loadCategories() {
        try {
            if (careerPathController != null) {
                List<String> categories = careerPathController.getAllCategories();
                ObservableList<String> categoryList = FXCollections.observableArrayList("All Categories");
                categoryList.addAll(categories);
                categoryComboBox.setItems(categoryList);
                categoryComboBox.setValue("All Categories");
            } else {
                ObservableList<String> categoryList = FXCollections.observableArrayList("All Categories");
                categoryComboBox.setItems(categoryList);
                categoryComboBox.setValue("All Categories");
            }
        } catch (Exception e) {
            System.err.println("Error loading categories: " + e.getMessage());
            ObservableList<String> categoryList = FXCollections.observableArrayList("All Categories");
            categoryComboBox.setItems(categoryList);
            categoryComboBox.setValue("All Categories");
        }
    }

    private void loadEducationLevels() {
        try {
            if (careerPathController != null) {
                List<String> educationLevels = careerPathController.getAllEducationLevels();
                ObservableList<String> educationList = FXCollections.observableArrayList("All Education Levels");
                educationList.addAll(educationLevels);
                educationComboBox.setItems(educationList);
                educationComboBox.setValue("All Education Levels");
            } else {
                ObservableList<String> educationList = FXCollections.observableArrayList("All Education Levels");
                educationComboBox.setItems(educationList);
                educationComboBox.setValue("All Education Levels");
            }
        } catch (Exception e) {
            System.err.println("Error loading education levels: " + e.getMessage());
            ObservableList<String> educationList = FXCollections.observableArrayList("All Education Levels");
            educationComboBox.setItems(educationList);
            educationComboBox.setValue("All Education Levels");
        }
    }

    private void showCareerPathDetails(CareerPath careerPath) {
        // Clear previous details
        careerDetailsVBox.getChildren().clear();

        // Create detail labels
        Label nameLabel = new Label("Career Path: " + careerPath.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label categoryLabel = new Label("Category: " + careerPath.getCategory());
        Label descriptionLabel = new Label("Description: " + careerPath.getDescription());
        descriptionLabel.setWrapText(true);

        careerDetailsVBox.getChildren().addAll(nameLabel, categoryLabel, descriptionLabel);

        if (careerPath.getEducationLevel() != null) {
            Label educationLabel = new Label("Education Level: " + careerPath.getEducationLevel());
            careerDetailsVBox.getChildren().add(educationLabel);
        }

        if (careerPath.getSalaryRange() != null) {
            Label salaryLabel = new Label("Salary Range: " + careerPath.getSalaryRange());
            careerDetailsVBox.getChildren().add(salaryLabel);
        }

        if (careerPath.getJobOutlook() != null) {
            Label outlookLabel = new Label("Job Outlook: " + careerPath.getJobOutlook());
            careerDetailsVBox.getChildren().add(outlookLabel);
        }

        if (!careerPath.getRequiredSkills().isEmpty()) {
            Label requiredSkillsLabel = new Label("Required Skills: " + String.join(", ", careerPath.getRequiredSkills()));
            requiredSkillsLabel.setWrapText(true);
            careerDetailsVBox.getChildren().add(requiredSkillsLabel);
        }

        if (!careerPath.getRecommendedSkills().isEmpty()) {
            Label recommendedSkillsLabel = new Label("Recommended Skills: " + String.join(", ", careerPath.getRecommendedSkills()));
            recommendedSkillsLabel.setWrapText(true);
            careerDetailsVBox.getChildren().add(recommendedSkillsLabel);
        }
    }

    private void updateWelcomeMessage() {
        if (currentUser != null) {
            welcomeLabel.setText("Welcome, " + currentUser.getUsername() + "!");
        }
    }

    private void closeDashboardWindow() {
        if (welcomeLabel.getScene() != null && welcomeLabel.getScene().getWindow() != null) {
            welcomeLabel.getScene().getWindow().hide();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
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

    public void setCareerPathController(CareerPathController careerPathController) {
        this.careerPathController = careerPathController;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        updateWelcomeMessage();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
