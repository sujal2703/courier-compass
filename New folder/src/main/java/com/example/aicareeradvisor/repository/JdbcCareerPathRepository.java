package com.example.aicareeradvisor.repository;

import com.example.aicareeradvisor.entity.CareerPath;
import com.example.aicareeradvisor.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcCareerPathRepository implements CareerPathRepository {

    @Override
    public CareerPath save(CareerPath careerPath) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            if (careerPath.getId() == null) {
                // Insert new career path
                String sql = "INSERT INTO career_paths (name, description, category, required_skills, recommended_skills, education_level, salary_range, job_outlook, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, careerPath.getName());
                stmt.setString(2, careerPath.getDescription());
                stmt.setString(3, careerPath.getCategory());
                stmt.setString(4, String.join(",", careerPath.getRequiredSkills()));
                stmt.setString(5, String.join(",", careerPath.getRecommendedSkills()));
                stmt.setString(6, careerPath.getEducationLevel());
                stmt.setString(7, careerPath.getSalaryRange());
                stmt.setString(8, careerPath.getJobOutlook());
                LocalDateTime now = LocalDateTime.now();
                stmt.setTimestamp(9, Timestamp.valueOf(now));
                stmt.setTimestamp(10, Timestamp.valueOf(now));
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    careerPath.setId(rs.getLong(1));
                }
                careerPath.setCreatedAt(now);
                careerPath.setUpdatedAt(now);
            } else {
                // Update existing career path
                String sql = "UPDATE career_paths SET name = ?, description = ?, category = ?, required_skills = ?, recommended_skills = ?, education_level = ?, salary_range = ?, job_outlook = ?, updated_at = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, careerPath.getName());
                stmt.setString(2, careerPath.getDescription());
                stmt.setString(3, careerPath.getCategory());
                stmt.setString(4, String.join(",", careerPath.getRequiredSkills()));
                stmt.setString(5, String.join(",", careerPath.getRecommendedSkills()));
                stmt.setString(6, careerPath.getEducationLevel());
                stmt.setString(7, careerPath.getSalaryRange());
                stmt.setString(8, careerPath.getJobOutlook());
                stmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setLong(10, careerPath.getId());
                stmt.executeUpdate();
                careerPath.setUpdatedAt(LocalDateTime.now());
            }
            return careerPath;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving career path", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public Optional<CareerPath> findById(Long id) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM career_paths WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CareerPath careerPath = mapResultSetToCareerPath(rs);
                return Optional.of(careerPath);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding career path by id", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public List<CareerPath> findAll() {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM career_paths";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<CareerPath> careerPaths = new ArrayList<>();
            while (rs.next()) {
                careerPaths.add(mapResultSetToCareerPath(rs));
            }
            return careerPaths;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all career paths", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "DELETE FROM career_paths WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting career path by id", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public void delete(CareerPath careerPath) {
        deleteById(careerPath.getId());
    }

    @Override
    public List<CareerPath> findByNameContaining(String name) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM career_paths WHERE name LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            List<CareerPath> careerPaths = new ArrayList<>();
            while (rs.next()) {
                careerPaths.add(mapResultSetToCareerPath(rs));
            }
            return careerPaths;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding career paths by name containing", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public List<CareerPath> findByCategory(String category) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM career_paths WHERE category = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            List<CareerPath> careerPaths = new ArrayList<>();
            while (rs.next()) {
                careerPaths.add(mapResultSetToCareerPath(rs));
            }
            return careerPaths;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding career paths by category", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public List<CareerPath> findByEducationLevel(String educationLevel) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM career_paths WHERE education_level = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, educationLevel);
            ResultSet rs = stmt.executeQuery();

            List<CareerPath> careerPaths = new ArrayList<>();
            while (rs.next()) {
                careerPaths.add(mapResultSetToCareerPath(rs));
            }
            return careerPaths;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding career paths by education level", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public Optional<CareerPath> findByName(String name) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM career_paths WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                CareerPath careerPath = mapResultSetToCareerPath(rs);
                return Optional.of(careerPath);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding career path by name", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public List<CareerPath> findByRequiredSkillsContaining(String skill) {
        return findAll().stream()
                .filter(cp -> cp.getRequiredSkills().stream()
                        .anyMatch(s -> s.toLowerCase().contains(skill.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public List<CareerPath> findByRecommendedSkillsContaining(String skill) {
        return findAll().stream()
                .filter(cp -> cp.getRecommendedSkills().stream()
                        .anyMatch(s -> s.toLowerCase().contains(skill.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public List<CareerPath> findByCategoryAndEducationLevel(String category, String educationLevel) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM career_paths WHERE category = ? AND education_level = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, category);
            stmt.setString(2, educationLevel);
            ResultSet rs = stmt.executeQuery();

            List<CareerPath> careerPaths = new ArrayList<>();
            while (rs.next()) {
                careerPaths.add(mapResultSetToCareerPath(rs));
            }
            return careerPaths;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding career paths by category and education level", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    @Override
    public long count() {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM career_paths";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error counting career paths", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public long countByCategory(String category) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM career_paths WHERE category = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error counting career paths by category", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    private CareerPath mapResultSetToCareerPath(ResultSet rs) throws SQLException {
        CareerPath careerPath = new CareerPath();
        careerPath.setId(rs.getLong("id"));
        careerPath.setName(rs.getString("name"));
        careerPath.setDescription(rs.getString("description"));
        careerPath.setCategory(rs.getString("category"));

        String requiredSkillsStr = rs.getString("required_skills");
        if (requiredSkillsStr != null && !requiredSkillsStr.trim().isEmpty()) {
            careerPath.setRequiredSkills(Arrays.asList(requiredSkillsStr.split(",")));
        }

        String recommendedSkillsStr = rs.getString("recommended_skills");
        if (recommendedSkillsStr != null && !recommendedSkillsStr.trim().isEmpty()) {
            careerPath.setRecommendedSkills(Arrays.asList(recommendedSkillsStr.split(",")));
        }

        careerPath.setEducationLevel(rs.getString("education_level"));
        careerPath.setSalaryRange(rs.getString("salary_range"));
        careerPath.setJobOutlook(rs.getString("job_outlook"));
        careerPath.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        careerPath.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return careerPath;
    }
}
