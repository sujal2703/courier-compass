package com.example.aicareeradvisor.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.aicareeradvisor.entity.CareerPath;
import com.example.aicareeradvisor.repository.CareerPathRepository;

public class CareerPathService {
    
    private final CareerPathRepository careerPathRepository;
    
    public CareerPathService(CareerPathRepository careerPathRepository) {
        this.careerPathRepository = careerPathRepository;
    }
    
    // Create new career path
    public boolean createCareerPath(String name, String description, String category) {
        if (name == null || name.trim().isEmpty() || 
            description == null || description.trim().isEmpty() ||
            category == null || category.trim().isEmpty()) {
            return false;
        }
        
        if (careerPathRepository.existsByName(name)) {
            return false;
        }
        
        CareerPath careerPath = new CareerPath(name, description, category);
        careerPathRepository.save(careerPath);
        return true;
    }
    
    // Get career path by ID
    public Optional<CareerPath> getCareerPathById(Long id) {
        return careerPathRepository.findById(id);
    }
    
    // Get career path by name
    public Optional<CareerPath> getCareerPathByName(String name) {
        return careerPathRepository.findByName(name);
    }
    
    // Get all career paths
    public List<CareerPath> getAllCareerPaths() {
        return careerPathRepository.findAll();
    }
    
    // Update career path
    public boolean updateCareerPath(CareerPath careerPath) {
        if (careerPath == null || careerPath.getId() == null) {
            return false;
        }
        
        Optional<CareerPath> existingCareerPath = careerPathRepository.findById(careerPath.getId());
        if (existingCareerPath.isPresent()) {
            careerPath.setUpdatedAt(java.time.LocalDateTime.now());
            careerPathRepository.save(careerPath);
            return true;
        }
        return false;
    }
    
    // Delete career path
    public boolean deleteCareerPath(Long id) {
        if (id == null) {
            return false;
        }
        
        Optional<CareerPath> careerPath = careerPathRepository.findById(id);
        if (careerPath.isPresent()) {
            careerPathRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // Search career paths by name
    public List<CareerPath> searchCareerPathsByName(String name) {
        return careerPathRepository.findByNameContaining(name);
    }
    
    // Get career paths by category
    public List<CareerPath> getCareerPathsByCategory(String category) {
        return careerPathRepository.findByCategory(category);
    }
    
    // Get career paths by education level
    public List<CareerPath> getCareerPathsByEducationLevel(String educationLevel) {
        return careerPathRepository.findByEducationLevel(educationLevel);
    }
    
    // Get career paths by category and education level
    public List<CareerPath> getCareerPathsByCategoryAndEducation(String category, String educationLevel) {
        return careerPathRepository.findByCategoryAndEducationLevel(category, educationLevel);
    }
    
    // Search career paths by required skills
    public List<CareerPath> searchCareerPathsByRequiredSkills(String skill) {
        return careerPathRepository.findByRequiredSkillsContaining(skill);
    }
    
    // Search career paths by recommended skills
    public List<CareerPath> searchCareerPathsByRecommendedSkills(String skill) {
        return careerPathRepository.findByRecommendedSkillsContaining(skill);
    }
    
    // Get career recommendations based on skills
    public List<CareerPath> getCareerRecommendations(List<String> userSkills) {
        if (userSkills == null || userSkills.isEmpty()) {
            return getAllCareerPaths();
        }
        
        return getAllCareerPaths().stream()
                .filter(careerPath -> {
                    // Check if user has any of the required skills
                    boolean hasRequiredSkills = careerPath.getRequiredSkills().stream()
                            .anyMatch(userSkills::contains);
                    
                    // Check if user has any of the recommended skills
                    boolean hasRecommendedSkills = careerPath.getRecommendedSkills().stream()
                            .anyMatch(userSkills::contains);
                    
                    return hasRequiredSkills || hasRecommendedSkills;
                })
                .sorted((cp1, cp2) -> {
                    // Sort by number of matching skills (descending)
                    long cp1Matches = cp1.getRequiredSkills().stream()
                            .filter(userSkills::contains).count() +
                            cp1.getRecommendedSkills().stream()
                            .filter(userSkills::contains).count();
                    
                    long cp2Matches = cp2.getRequiredSkills().stream()
                            .filter(userSkills::contains).count() +
                            cp2.getRecommendedSkills().stream()
                            .filter(userSkills::contains).count();
                    
                    return Long.compare(cp2Matches, cp1Matches);
                })
                .collect(Collectors.toList());
    }
    
    // Get all categories
    public List<String> getAllCategories() {
        return getAllCareerPaths().stream()
                .map(CareerPath::getCategory)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    // Get all education levels
    public List<String> getAllEducationLevels() {
        return getAllCareerPaths().stream()
                .map(CareerPath::getEducationLevel)
                .filter(level -> level != null && !level.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
    
    // Get statistics
    public long getTotalCareerPaths() {
        return careerPathRepository.count();
    }
    
    public long getCareerPathCountByCategory(String category) {
        return careerPathRepository.countByCategory(category);
    }
}
