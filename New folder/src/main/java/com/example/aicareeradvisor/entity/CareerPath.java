package com.example.aicareeradvisor.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class CareerPath {
    private Long id;
    private String name;
    private String description;
    private String category;
    private List<String> requiredSkills;
    private List<String> recommendedSkills;
    private String educationLevel;
    private String salaryRange;
    private String jobOutlook;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public CareerPath() {
        this.requiredSkills = new ArrayList<>();
        this.recommendedSkills = new ArrayList<>();
    }

    public CareerPath(String name, String description, String category) {
        this();
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public List<String> getRecommendedSkills() { return recommendedSkills; }
    public void setRecommendedSkills(List<String> recommendedSkills) { this.recommendedSkills = recommendedSkills; }

    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }

    public String getSalaryRange() { return salaryRange; }
    public void setSalaryRange(String salaryRange) { this.salaryRange = salaryRange; }

    public String getJobOutlook() { return jobOutlook; }
    public void setJobOutlook(String jobOutlook) { this.jobOutlook = jobOutlook; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Helper methods
    public void addRequiredSkill(String skill) {
        if (!requiredSkills.contains(skill)) {
            requiredSkills.add(skill);
        }
    }

    public void addRecommendedSkill(String skill) {
        if (!recommendedSkills.contains(skill)) {
            recommendedSkills.add(skill);
        }
    }

    @Override
    public String toString() {
        return "CareerPath{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", educationLevel='" + educationLevel + '\'' +
                '}';
    }
}
