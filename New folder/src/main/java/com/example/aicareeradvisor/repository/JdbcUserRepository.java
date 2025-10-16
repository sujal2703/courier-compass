package com.example.aicareeradvisor.repository;

import com.example.aicareeradvisor.entity.User;
import com.example.aicareeradvisor.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepository implements UserRepository {

    @Override
    public User save(User user) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            if (user.getId() == null) {
                // Insert new user
                String sql = "INSERT INTO users (username, password, email, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                LocalDateTime now = LocalDateTime.now();
                stmt.setTimestamp(4, Timestamp.valueOf(now));
                stmt.setTimestamp(5, Timestamp.valueOf(now));
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
                user.setCreatedAt(now);
                user.setUpdatedAt(now);
            } else {
                // Update existing user
                String sql = "UPDATE users SET username = ?, password = ?, email = ?, updated_at = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setLong(5, user.getId());
                stmt.executeUpdate();
                user.setUpdatedAt(LocalDateTime.now());
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by id", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public List<User> findAll() {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all users", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user by id", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public void delete(User user) {
        deleteById(user.getId());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by email", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public List<User> findByUsernameContaining(String username) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + username + "%");
            ResultSet rs = stmt.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding users by username containing", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username and password", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public long count() {
        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM users";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error counting users", e);
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    }
}
