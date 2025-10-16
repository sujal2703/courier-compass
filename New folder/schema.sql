-- Database schema for AI Career Advisor
-- Run this script in your MySQL database to create the required tables

-- Create database (uncomment if needed)
-- CREATE DATABASE IF NOT EXISTS ai_career_advisor;
-- USE ai_career_advisor;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Career paths table
CREATE TABLE IF NOT EXISTS career_paths (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    category VARCHAR(100),
    required_skills TEXT,  -- Comma-separated list
    recommended_skills TEXT,  -- Comma-separated list
    education_level VARCHAR(100),
    salary_range VARCHAR(100),
    job_outlook VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data (optional)
INSERT IGNORE INTO users (username, password, email) VALUES
('admin', '5f4dcc3b5aa765d61d8327deb882cf99', 'admin@example.com'),  -- password123 hashed with MD5 for demo
('testuser', 'cc03e747a6afbbcbf8be7668acfebee5', 'test@example.com');  -- test123 hashed with MD5 for demo

INSERT IGNORE INTO career_paths (name, description, category, required_skills, recommended_skills) VALUES
('Software Developer', 'Develop applications and software solutions using programming languages and frameworks.', 'Technology', 'Java,Python,JavaScript', 'SQL,Git,Agile'),
('Data Scientist', 'Analyze complex data sets to help organizations make better decisions.', 'Technology', 'Python,R,Statistics', 'Machine Learning,SQL,Tableau'),
('Marketing Manager', 'Plan and execute marketing strategies to promote products and services.', 'Business', 'Marketing,Strategy,Communication', 'Digital Marketing,SEO,Analytics'),
('Nurse', 'Provide patient care and support in healthcare settings.', 'Healthcare', 'Patient Care,Medical Knowledge,Empathy', 'Communication,Organization,CPR'),
('Teacher', 'Educate students in various subjects and grade levels.', 'Education', 'Subject Knowledge,Pedagogy,Communication', 'Classroom Management,Technology,Assessment');
