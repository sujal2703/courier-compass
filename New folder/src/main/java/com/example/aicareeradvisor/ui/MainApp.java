
package com.example.aicareeradvisor.ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.aicareeradvisor.controller.CareerPathController;
import com.example.aicareeradvisor.controller.UserController;
import com.example.aicareeradvisor.entity.CareerPath;
import com.example.aicareeradvisor.entity.User;
import com.example.aicareeradvisor.repository.CareerPathRepository;
import com.example.aicareeradvisor.repository.UserRepository;
import com.example.aicareeradvisor.service.CareerPathService;
import com.example.aicareeradvisor.service.UserService;
import com.example.aicareeradvisor.ui.controller.DashboardController;
import com.example.aicareeradvisor.ui.controller.LoginController;
import com.example.aicareeradvisor.ui.controller.QuizController;
import com.example.aicareeradvisor.ui.controller.RegisterController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private UserController userController;
    private CareerPathController careerPathController;

    @Override
    public void init() throws Exception {
        super.init();

        // Initialize repositories (you would typically use dependency injection here)
        // Replaced in-memory repositories with JDBC implementations
        UserRepository userRepository = new com.example.aicareeradvisor.repository.JdbcUserRepository();
        CareerPathRepository careerPathRepository = new com.example.aicareeradvisor.repository.JdbcCareerPathRepository();

        // Initialize services
        UserService userService = new UserService(userRepository);
        CareerPathService careerPathService = new CareerPathService(careerPathRepository);

        // Initialize controllers
        userController = new UserController(userService);
        careerPathController = new CareerPathController(careerPathService);

        // Initialize with sample data
        initializeSampleData();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the login view as the initial screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();

        // Get the controller and inject dependencies
        LoginController loginController = loader.getController();
        if (loginController != null) {
            loginController.setUserController(userController);
            loginController.setMainApp(this);
        }

        primaryStage.setTitle("AI Career Advisor");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }

    public void showRegisterView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        Parent root = loader.load();

        RegisterController registerController = loader.getController();
        if (registerController != null) {
            registerController.setUserController(userController);
            registerController.setMainApp(this);
        }

        Stage stage = new Stage();
        stage.setTitle("Register - AI Career Advisor");
        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }

    public void showDashboardView(User user) throws IOException {
        System.out.println("Opening dashboard for user: " + user.getUsername());
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            System.out.println("Loading dashboard FXML...");
            Parent root = loader.load();
            System.out.println("Dashboard FXML loaded successfully");

            DashboardController dashboardController = loader.getController();
            if (dashboardController != null) {
                System.out.println("Setting up dashboard controller...");
                dashboardController.setUserController(userController);
                dashboardController.setCareerPathController(careerPathController);
                dashboardController.setCurrentUser(user);
                dashboardController.setMainApp(this);
                System.out.println("Dashboard controller setup complete");
            } else {
                System.out.println("WARNING: Dashboard controller is null!");
            }

            Stage stage = new Stage();
            stage.setTitle("Dashboard - AI Career Advisor");
            stage.setScene(new Scene(root, 1000, 700));
            stage.setMaximized(true);
            System.out.println("Showing dashboard stage...");
            stage.show();
            System.out.println("Dashboard opened successfully");
        } catch (Exception e) {
            System.err.println("Error opening dashboard: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void showQuizView(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/quiz.fxml"));
        Parent root = loader.load();

        QuizController quizController = loader.getController();
        if (quizController != null) {
            quizController.setCareerPathController(careerPathController);
            quizController.setCurrentUser(user);
            quizController.setMainApp(this);
        }

        Stage stage = new Stage();
        stage.setTitle("Career Assessment Quiz - AI Career Advisor");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    private void initializeSampleData() {
        System.out.println("Initializing sample data...");
        
        // Add sample users
        System.out.println("Creating sample users...");
        userController.registerUser("admin", "password123", "admin@example.com");
        userController.registerUser("testuser", "test123", "test@example.com");
        System.out.println("Sample users created");
        
        // Add sample career paths
        System.out.println("Creating sample career paths...");
        careerPathController.createCareerPath(
            "Software Developer",
            "Develop applications and software solutions using programming languages and frameworks.",
            "Technology"
        );

        careerPathController.createCareerPath(
            "Data Scientist",
            "Analyze complex data sets to help organizations make better decisions.",
            "Technology"
        );

        careerPathController.createCareerPath(
            "Marketing Manager",
            "Plan and execute marketing strategies to promote products and services.",
            "Business"
        );

        careerPathController.createCareerPath(
            "Nurse",
            "Provide patient care and support in healthcare settings.",
            "Healthcare"
        );

        careerPathController.createCareerPath(
            "Teacher",
            "Educate students in various subjects and grade levels.",
            "Education"
        );
        System.out.println("Sample data initialization complete");
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Simple in-memory implementations for demonstration
    private static class InMemoryUserRepository implements UserRepository {
        private final Map<Long, User> users = new HashMap<>();
        private final Map<String, User> usersByUsername = new HashMap<>();
        private final Map<String, User> usersByEmail = new HashMap<>();
        private long nextId = 1;

        @Override
        public User save(User user) {
            if (user.getId() == null) {
                user.setId(nextId++);
                user.setCreatedAt(LocalDateTime.now());
            }
            user.setUpdatedAt(LocalDateTime.now());
            
            users.put(user.getId(), user);
            usersByUsername.put(user.getUsername(), user);
            usersByEmail.put(user.getEmail(), user);
            return user;
        }

        @Override
        public Optional<User> findById(Long id) {
            return Optional.ofNullable(users.get(id));
        }

        @Override
        public List<User> findAll() {
            return new ArrayList<>(users.values());
        }

        @Override
        public void deleteById(Long id) {
            User user = users.get(id);
            if (user != null) {
                users.remove(id);
                usersByUsername.remove(user.getUsername());
                usersByEmail.remove(user.getEmail());
            }
        }

        @Override
        public void delete(User user) {
            deleteById(user.getId());
        }

        @Override
        public Optional<User> findByUsername(String username) {
            return Optional.ofNullable(usersByUsername.get(username));
        }

        @Override
        public Optional<User> findByEmail(String email) {
            return Optional.ofNullable(usersByEmail.get(email));
        }

        @Override
        public List<User> findByUsernameContaining(String username) {
            return users.values().stream()
                .filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public Optional<User> findByUsernameAndPassword(String username, String password) {
            return findByUsername(username)
                .filter(user -> user.getPassword().equals(password));
        }

        @Override
        public boolean existsByUsername(String username) {
            return usersByUsername.containsKey(username);
        }

        @Override
        public boolean existsByEmail(String email) {
            return usersByEmail.containsKey(email);
        }

        @Override
        public long count() {
            return users.size();
        }
    }

    private static class InMemoryCareerPathRepository implements CareerPathRepository {
        private final Map<Long, CareerPath> careerPaths = new HashMap<>();
        private final Map<String, CareerPath> careerPathsByName = new HashMap<>();
        private long nextId = 1;

        @Override
        public CareerPath save(CareerPath careerPath) {
            if (careerPath.getId() == null) {
                careerPath.setId(nextId++);
                careerPath.setCreatedAt(LocalDateTime.now());
            }
            careerPath.setUpdatedAt(LocalDateTime.now());
            
            careerPaths.put(careerPath.getId(), careerPath);
            careerPathsByName.put(careerPath.getName(), careerPath);
            return careerPath;
        }

        @Override
        public Optional<CareerPath> findById(Long id) {
            return Optional.ofNullable(careerPaths.get(id));
        }

        @Override
        public List<CareerPath> findAll() {
            return new ArrayList<>(careerPaths.values());
        }

        @Override
        public void deleteById(Long id) {
            CareerPath careerPath = careerPaths.get(id);
            if (careerPath != null) {
                careerPaths.remove(id);
                careerPathsByName.remove(careerPath.getName());
            }
        }

        @Override
        public void delete(CareerPath careerPath) {
            deleteById(careerPath.getId());
        }

        @Override
        public List<CareerPath> findByNameContaining(String name) {
            return careerPaths.values().stream()
                .filter(cp -> cp.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public List<CareerPath> findByCategory(String category) {
            return careerPaths.values().stream()
                .filter(cp -> category.equals(cp.getCategory()))
                .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public List<CareerPath> findByEducationLevel(String educationLevel) {
            return careerPaths.values().stream()
                .filter(cp -> educationLevel.equals(cp.getEducationLevel()))
                .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public Optional<CareerPath> findByName(String name) {
            return Optional.ofNullable(careerPathsByName.get(name));
        }

        @Override
        public List<CareerPath> findByRequiredSkillsContaining(String skill) {
            return careerPaths.values().stream()
                .filter(cp -> cp.getRequiredSkills().stream()
                    .anyMatch(s -> s.toLowerCase().contains(skill.toLowerCase())))
                .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public List<CareerPath> findByRecommendedSkillsContaining(String skill) {
            return careerPaths.values().stream()
                .filter(cp -> cp.getRecommendedSkills().stream()
                    .anyMatch(s -> s.toLowerCase().contains(skill.toLowerCase())))
                .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public List<CareerPath> findByCategoryAndEducationLevel(String category, String educationLevel) {
            return careerPaths.values().stream()
                .filter(cp -> category.equals(cp.getCategory()) && educationLevel.equals(cp.getEducationLevel()))
                .collect(java.util.stream.Collectors.toList());
        }

        @Override
        public boolean existsByName(String name) {
            return careerPathsByName.containsKey(name);
        }

        @Override
        public long count() {
            return careerPaths.size();
        }

        @Override
        public long countByCategory(String category) {
            return careerPaths.values().stream()
                .filter(cp -> category.equals(cp.getCategory()))
                .count();
        }
    }
}
