package com.example.aicareeradvisor.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.aicareeradvisor.controller.CareerPathController;
import com.example.aicareeradvisor.entity.CareerPath;
import com.example.aicareeradvisor.entity.User;
import com.example.aicareeradvisor.ui.MainApp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class QuizController {

    @FXML
    private Label questionLabel;

    @FXML
    private VBox answerOptionsVBox;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button submitButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label questionNumberLabel;

    private CareerPathController careerPathController;
    private User currentUser;
    private MainApp mainApp;

    // Quiz data
    private List<QuizQuestion> questions;
    private int currentQuestionIndex;
    private Map<String, Integer> userAnswers;
    private List<String> userSkills;
    private ToggleGroup answerToggleGroup;

    @FXML
    private void initialize() {
        // Initialize quiz data
        initializeQuestions();
        currentQuestionIndex = 0;
        userAnswers = new HashMap<>();
        userSkills = new ArrayList<>();
        answerToggleGroup = new ToggleGroup();

        // Set up event handlers
        previousButton.setOnAction(event -> handlePrevious());
        nextButton.setOnAction(event -> handleNext());
        submitButton.setOnAction(event -> handleSubmit());

        // Load first question
        loadQuestion(currentQuestionIndex);
        updateNavigationButtons();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();

        // Personality and preference questions
        questions.add(new QuizQuestion(
            "How do you prefer to spend your free time?",
            Arrays.asList("Reading and learning new things", "Socializing with friends",
                         "Working on creative projects", "Solving puzzles and problems"),
            Arrays.asList("Analytical", "Social", "Creative", "Technical")
        ));

        questions.add(new QuizQuestion(
            "What type of work environment do you prefer?",
            Arrays.asList("Quiet, focused environment", "Dynamic, fast-paced environment",
                         "Collaborative team setting", "Independent, flexible setting"),
            Arrays.asList("Analytical", "Dynamic", "Social", "Creative")
        ));

        questions.add(new QuizQuestion(
            "How do you approach problem-solving?",
            Arrays.asList("Analyze data and research thoroughly", "Trust your intuition and experience",
                         "Collaborate with others for solutions", "Experiment with different approaches"),
            Arrays.asList("Analytical", "Intuitive", "Social", "Creative")
        ));

        questions.add(new QuizQuestion(
            "What motivates you most in a job?",
            Arrays.asList("Intellectual challenges and learning", "Helping others and making a difference",
                         "Creative expression and innovation", "Achieving goals and recognition"),
            Arrays.asList("Analytical", "Social", "Creative", "Achievement")
        ));

        questions.add(new QuizQuestion(
            "How do you prefer to communicate?",
            Arrays.asList("Written communication and documentation", "Face-to-face conversations",
                         "Visual presentations and demonstrations", "Data and charts"),
            Arrays.asList("Analytical", "Social", "Creative", "Technical")
        ));

        questions.add(new QuizQuestion(
            "What are your strongest skills?",
            Arrays.asList("Mathematics and logical thinking", "Communication and empathy",
                         "Artistic and design abilities", "Technology and programming"),
            Arrays.asList("Technical", "Social", "Creative", "Technical")
        ));

        questions.add(new QuizQuestion(
            "How do you handle stress and pressure?",
            Arrays.asList("Plan and organize systematically", "Seek support from others",
                         "Use creativity to find solutions", "Focus on technical solutions"),
            Arrays.asList("Analytical", "Social", "Creative", "Technical")
        ));

        questions.add(new QuizQuestion(
            "What type of projects do you enjoy most?",
            Arrays.asList("Research and analysis projects", "Team collaboration projects",
                         "Creative and design projects", "Technical implementation projects"),
            Arrays.asList("Analytical", "Social", "Creative", "Technical")
        ));
    }

    @FXML
    private void handlePrevious() {
        if (currentQuestionIndex > 0) {
            saveCurrentAnswer();
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
            updateNavigationButtons();
        }
    }

    @FXML
    private void handleNext() {
        if (currentQuestionIndex < questions.size() - 1) {
            saveCurrentAnswer();
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
            updateNavigationButtons();
        }
    }

    @FXML
    private void handleSubmit() {
        saveCurrentAnswer();
        processResults();
    }

    private void saveCurrentAnswer() {
        Toggle selectedToggle = answerToggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            RadioButton selectedRadioButton = (RadioButton) selectedToggle;
            String selectedAnswer = selectedRadioButton.getText();
            
            QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
            int answerIndex = currentQuestion.getOptions().indexOf(selectedAnswer);
            if (answerIndex >= 0) {
                userAnswers.put(currentQuestion.getQuestion(), answerIndex);
            }
        }
    }

    private void loadQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            QuizQuestion question = questions.get(index);

            // Update UI with question
            questionLabel.setText(question.getQuestion());
            questionNumberLabel.setText("Question " + (index + 1) + " of " + questions.size());

            // Load answer options
            loadAnswerOptions(question.getOptions());

            // Update progress
            double progress = (double) (index + 1) / questions.size();
            progressBar.setProgress(progress);
        }
    }

    private void loadAnswerOptions(List<String> options) {
        // Clear existing options and add new ones
        answerOptionsVBox.getChildren().clear();
        answerToggleGroup.getToggles().clear();

        for (String option : options) {
            RadioButton radioButton = new RadioButton(option);
            radioButton.setToggleGroup(answerToggleGroup);
            answerOptionsVBox.getChildren().add(radioButton);
        }

        // Restore previous answer if exists
        QuizQuestion currentQuestion = questions.get(currentQuestionIndex);
        Integer previousAnswer = userAnswers.get(currentQuestion.getQuestion());
        if (previousAnswer != null && previousAnswer < options.size()) {
            RadioButton previousRadioButton = (RadioButton) answerOptionsVBox.getChildren().get(previousAnswer);
            previousRadioButton.setSelected(true);
        }
    }

    private void updateNavigationButtons() {
        previousButton.setDisable(currentQuestionIndex == 0);
        nextButton.setDisable(currentQuestionIndex == questions.size() - 1);
        submitButton.setVisible(currentQuestionIndex == questions.size() - 1);
    }

    private void processResults() {
        // Analyze user answers and determine skills
        analyzeUserAnswers();

        // Get career recommendations based on skills
        List<CareerPath> recommendations = careerPathController.getCareerRecommendations(userSkills);

        // Show results
        showQuizResults(recommendations);
    }

    private void analyzeUserAnswers() {
        userSkills.clear();

        for (Map.Entry<String, Integer> entry : userAnswers.entrySet()) {
            String question = entry.getKey();
            Integer answerIndex = entry.getValue();

            // Find the question and get the corresponding skill
            for (QuizQuestion q : questions) {
                if (q.getQuestion().equals(question) && answerIndex < q.getSkills().size()) {
                    String skill = q.getSkills().get(answerIndex);
                    if (!userSkills.contains(skill)) {
                        userSkills.add(skill);
                    }
                    break;
                }
            }
        }

        // Add some default skills based on common patterns
        if (userSkills.contains("Technical")) {
            userSkills.add("Programming");
            userSkills.add("Problem Solving");
        }

        if (userSkills.contains("Social")) {
            userSkills.add("Communication");
            userSkills.add("Leadership");
        }

        if (userSkills.contains("Creative")) {
            userSkills.add("Design");
            userSkills.add("Innovation");
        }

        if (userSkills.contains("Analytical")) {
            userSkills.add("Data Analysis");
            userSkills.add("Research");
        }
    }

    private void showQuizResults(List<CareerPath> recommendations) {
        StringBuilder results = new StringBuilder();
        results.append("Quiz Results\n\n");
        results.append("Your identified skills:\n");
        for (String skill : userSkills) {
            results.append("- ").append(skill).append("\n");
        }

        results.append("\nRecommended Career Paths:\n");
        for (int i = 0; i < Math.min(recommendations.size(), 5); i++) {
            CareerPath path = recommendations.get(i);
            results.append(i + 1).append(". ").append(path.getName())
                   .append(" (").append(path.getCategory()).append(")\n");
        }

        showAlert(Alert.AlertType.INFORMATION, "Quiz Results", results.toString());
        closeQuizWindow();
    }

    private void closeQuizWindow() {
        if (questionLabel.getScene() != null && questionLabel.getScene().getWindow() != null) {
            questionLabel.getScene().getWindow().hide();
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
    public void setCareerPathController(CareerPathController careerPathController) {
        this.careerPathController = careerPathController;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    // Inner class for quiz questions
    private static class QuizQuestion {
        private String question;
        private List<String> options;
        private List<String> skills;

        public QuizQuestion(String question, List<String> options, List<String> skills) {
            this.question = question;
            this.options = options;
            this.skills = skills;
        }

        public String getQuestion() { return question; }
        public List<String> getOptions() { return options; }
        public List<String> getSkills() { return skills; }
    }
}
