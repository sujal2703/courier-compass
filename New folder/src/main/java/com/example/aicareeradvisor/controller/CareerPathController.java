package com.example.aicareeradvisor.controller;

import com.example.aicareeradvisor.entity.CareerPath;
import com.example.aicareeradvisor.service.CareerPathService;
import java.util.List;
import java.util.Optional;

public class CareerPathController {

    private final CareerPathService careerPathService;

    public CareerPathController(CareerPathService careerPathService) {
        this.careerPathService = careerPathService;
    }

    // Create career path
    public boolean createCareerPath(String name, String description, String category) {
        return careerPathService.createCareerPath(name, description, category);
    }

    // Get career path by ID
    public Optional<CareerPath> getCareerPathById(Long id) {
        return careerPathService.getCareerPathById(id);
    }

    // Get career path by name
    public Optional<CareerPath> getCareerPathByName(String name) {
        return careerPathService.getCareerPathByName(name);
    }

    // Get all career paths
    public List<CareerPath> getAllCareerPaths() {
        return careerPathService.getAllCareerPaths();
    }

    // Update career path
    public boolean updateCareerPath(CareerPath careerPath) {
        return careerPathService.updateCareerPath(careerPath);
    }

    // Delete career path
    public boolean deleteCareerPath(Long id) {
        return careerPathService.deleteCareerPath(id);
    }

    // Search career paths
    public List<CareerPath> searchCareerPathsByName(String name) {
        return careerPathService.searchCareerPathsByName(name);
    }

    // Get career paths by category
    public List<CareerPath> getCareerPathsByCategory(String category) {
        return careerPathService.getCareerPathsByCategory(category);
    }

    // Get career paths by education level
    public List<CareerPath> getCareerPathsByEducationLevel(String educationLevel) {
        return careerPathService.getCareerPathsByEducationLevel(educationLevel);
    }

    // Get career paths by category and education
    public List<CareerPath> getCareerPathsByCategoryAndEducation(String category, String educationLevel) {
        return careerPathService.getCareerPathsByCategoryAndEducation(category, educationLevel);
    }

    // Search by skills
    public List<CareerPath> searchByRequiredSkills(String skill) {
        return careerPathService.searchCareerPathsByRequiredSkills(skill);
    }

    public List<CareerPath> searchByRecommendedSkills(String skill) {
        return careerPathService.searchCareerPathsByRecommendedSkills(skill);
    }

    // Get career recommendations
    public List<CareerPath> getCareerRecommendations(List<String> userSkills) {
        return careerPathService.getCareerRecommendations(userSkills);
    }

    // Get categories and education levels
    public List<String> getAllCategories() {
        return careerPathService.getAllCategories();
    }

    public List<String> getAllEducationLevels() {
        return careerPathService.getAllEducationLevels();
    }

    // Get statistics
    public long getTotalCareerPaths() {
        return careerPathService.getTotalCareerPaths();
    }

    public long getCareerPathCountByCategory(String category) {
        return careerPathService.getCareerPathCountByCategory(category);
    }

    // Validation methods
    public boolean isValidCareerPathName(String name) {
        return name != null && name.trim().length() >= 2 && name.trim().length() <= 100;
    }

    public boolean isValidDescription(String description) {
        return description != null && description.trim().length() >= 10 && description.trim().length() <= 1000;
    }

    public boolean isValidCategory(String category) {
        return category != null && category.trim().length() >= 2 && category.trim().length() <= 50;
    }

    public boolean isValidEducationLevel(String educationLevel) {
        if (educationLevel == null || educationLevel.trim().isEmpty()) {
            return true; // Optional field
        }
        String[] validLevels = {"High School", "Associate's Degree", "Bachelor's Degree",
                               "Master's Degree", "Doctorate", "Certificate", "Self-taught"};
        for (String level : validLevels) {
            if (level.equalsIgnoreCase(educationLevel.trim())) {
                return true;
            }
        }
        return false;
    }
}
