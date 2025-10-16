# AI Career Advisor - JavaFX Application

A comprehensive career guidance application built with JavaFX that helps users discover their ideal career paths through personality assessments and skill matching.

## 🚀 Features

### Core Functionality
- **User Authentication**: Secure login and registration system with password hashing
- **Career Path Exploration**: Browse and search through various career options
- **Interactive Quiz**: Personality assessment quiz to determine user skills and preferences
- **Smart Recommendations**: AI-powered career recommendations based on quiz results
- **Advanced Filtering**: Filter career paths by category, education level, and skills

### User Interface
- **Modern Design**: Clean, professional UI with gradient backgrounds and smooth animations
- **Responsive Layout**: Adapts to different screen sizes and window dimensions
- **Intuitive Navigation**: Easy-to-use interface with clear navigation paths
- **Professional Styling**: Custom CSS styling for a polished appearance

## 📁 Project Structure

```
src/main/java/com/example/aicareeradvisor/
├── AiCareerAdvisorApplication.java          # Main application entry point
├── entity/
│   ├── User.java                           # User entity with authentication data
│   └── CareerPath.java                     # Career path entity with detailed information
├── repository/
│   ├── UserRepository.java                 # Data access interface for users
│   └── CareerPathRepository.java           # Data access interface for career paths
├── service/
│   ├── UserService.java                    # Business logic for user operations
│   └── CareerPathService.java              # Business logic for career path operations
├── controller/
│   ├── UserController.java                 # Application logic for user management
│   └── CareerPathController.java           # Application logic for career path management
└── ui/
    ├── MainApp.java                        # JavaFX application setup and navigation
    ├── controller/
    │   ├── LoginController.java            # Login screen controller
    │   ├── RegisterController.java         # Registration screen controller
    │   ├── DashboardController.java        # Main dashboard controller
    │   └── QuizController.java             # Career assessment quiz controller
    └── util/
        └── HttpClientUtil.java             # HTTP client utilities for API calls

src/main/resources/
├── fxml/
│   ├── login.fxml                          # Login screen UI layout
│   ├── register.fxml                       # Registration screen UI layout
│   ├── dashboard.fxml                      # Main dashboard UI layout
│   └── quiz.fxml                           # Career quiz UI layout
└── styles.css                              # Application styling
```

## 🏗️ Architecture

### Design Patterns
- **MVC (Model-View-Controller)**: Clear separation of concerns
- **Repository Pattern**: Abstract data access layer
- **Service Layer**: Business logic encapsulation
- **Dependency Injection**: Loose coupling between components

### Key Components

#### Entities
- **User**: Represents application users with authentication data
- **CareerPath**: Represents career options with detailed information including skills, education requirements, and job outlook

#### Services
- **UserService**: Handles user registration, authentication, and profile management
- **CareerPathService**: Manages career path data, search, and recommendation algorithms

#### Controllers
- **UI Controllers**: Handle user interactions and screen navigation
- **Business Controllers**: Coordinate between services and UI layers

## 🎯 Core Features Explained

### 1. User Authentication System
- Secure password hashing using SHA-256
- Input validation for username, email, and password
- Duplicate user prevention
- Session management

### 2. Career Assessment Quiz
- 8 carefully crafted personality and preference questions
- Skill mapping based on user responses
- Dynamic question navigation with progress tracking
- Personalized career recommendations

### 3. Career Path Database
- Comprehensive career information including:
  - Required and recommended skills
  - Education level requirements
  - Salary range information
  - Job outlook data
  - Category classification

### 4. Smart Recommendation Engine
- Skill-based matching algorithm
- Category and education level filtering
- Relevance scoring and ranking
- Personalized suggestions

## 🛠️ Setup and Installation

### Prerequisites
- Java 11 or higher
- JavaFX SDK (included with Java 11+)
- Maven (for dependency management)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ai-career-advisor
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn javafx:run
   ```

### Configuration

The application uses in-memory data storage by default. For production use, you can implement the repository interfaces with:
- Database storage (MySQL, PostgreSQL)
- File-based storage
- Cloud storage solutions

## 🎨 UI/UX Features

### Design Principles
- **Accessibility**: High contrast colors and readable fonts
- **Responsiveness**: Adapts to different screen sizes
- **Consistency**: Uniform styling across all screens
- **Intuitiveness**: Clear navigation and user feedback

### Color Scheme
- Primary: #667eea (Blue gradient)
- Secondary: #764ba2 (Purple gradient)
- Background: #f5f5f5 (Light gray)
- Text: #495057 (Dark gray)

## 🔧 Customization

### Adding New Career Paths
1. Implement the `CareerPathRepository` interface
2. Add career path data through the service layer
3. Update the quiz questions and skill mappings as needed

### Modifying the Quiz
1. Edit the `QuizController.initializeQuestions()` method
2. Update the skill mappings for each question
3. Adjust the recommendation algorithm in `CareerPathService`

### Styling Changes
1. Modify `src/main/resources/styles.css`
2. Update FXML files for layout changes
3. Test across different screen sizes

## 🚀 Future Enhancements

### Planned Features
- **Database Integration**: Persistent data storage
- **Advanced Analytics**: User behavior tracking and insights
- **External APIs**: Integration with job market data
- **Mobile Support**: Cross-platform compatibility
- **AI Enhancement**: Machine learning for better recommendations

### Technical Improvements
- **Unit Testing**: Comprehensive test coverage
- **Performance Optimization**: Caching and query optimization
- **Security Enhancement**: JWT tokens and role-based access
- **Internationalization**: Multi-language support

## 📝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the documentation

---

**AI Career Advisor** - Empowering career decisions with intelligent guidance.
