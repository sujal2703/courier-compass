package com.example.aicareeradvisor;

import com.example.aicareeradvisor.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnectivityTest {
    public static void main(String[] args) {
        System.out.println("Testing JDBC Connectivity to MySQL Database...");
        System.out.println("Database URL: jdbc:mysql://localhost:3306/ai_career_advisor");
        System.out.println("User: root");
        System.out.println();

        Connection connection = null;
        try {
            connection = DatabaseUtil.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("✅ SUCCESS: JDBC Connectivity Test Passed!");
                System.out.println("Connection established successfully to the database.");
                System.out.println("Database is accessible and ready for operations.");
            } else {
                System.out.println("❌ FAILURE: Connection is null or closed.");
            }
        } catch (SQLException e) {
            System.out.println("❌ FAILURE: JDBC Connectivity Test Failed!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please check:");
            System.out.println("- MySQL server is running");
            System.out.println("- Database 'ai_career_advisor' exists");
            System.out.println("- Username and password are correct");
            System.out.println("- JDBC driver is properly loaded");
        } finally {
            DatabaseUtil.closeConnection(connection);
        }
    }
}
