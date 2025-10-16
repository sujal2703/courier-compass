package com.example.aicareeradvisor.repository;

import com.example.aicareeradvisor.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    
    // Basic CRUD operations
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
    void delete(User user);
    
    // Custom finder methods
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByUsernameContaining(String username);
    
    // Authentication methods
    Optional<User> findByUsernameAndPassword(String username, String password);
    
    // Utility methods
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    long count();
}
