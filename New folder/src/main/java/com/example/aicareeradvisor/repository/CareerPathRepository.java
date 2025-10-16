package com.example.aicareeradvisor.repository;

import com.example.aicareeradvisor.entity.CareerPath;
import java.util.List;
import java.util.Optional;

public interface CareerPathRepository {
    
    // Basic CRUD operations
    CareerPath save(CareerPath careerPath);
    Optional<CareerPath> findById(Long id);
    List<CareerPath> findAll();
    void deleteById(Long id);
    void delete(CareerPath careerPath);
    
    // Custom finder methods
    List<CareerPath> findByNameContaining(String name);
    List<CareerPath> findByCategory(String category);
    List<CareerPath> findByEducationLevel(String educationLevel);
    Optional<CareerPath> findByName(String name);
    
    // Advanced search methods
    List<CareerPath> findByRequiredSkillsContaining(String skill);
    List<CareerPath> findByRecommendedSkillsContaining(String skill);
    List<CareerPath> findByCategoryAndEducationLevel(String category, String educationLevel);
    
    // Utility methods
    boolean existsByName(String name);
    long count();
    long countByCategory(String category);
}
