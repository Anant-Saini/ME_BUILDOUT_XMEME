# 🎬 XMeme - Meme Stream Backend

<div align="center">

![Java](https://img.shields.io/badge/Java-11+-ED8B00?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.1-6DB33F?style=flat-square&logo=spring-boot)
![MongoDB](https://img.shields.io/badge/MongoDB-2.7.1-47A248?style=flat-square&logo=mongodb)
![License](https://img.shields.io/badge/License-Apache%202.0-blue?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-success?style=flat-square)

**A modern Spring Boot REST API backend for a meme streaming and sharing platform**

[Features](#-features) • [Tech Stack](#-tech-stack) • [Getting Started](#-getting-started) • [API Documentation](#-api-documentation) • [Project Structure](#-project-structure)

</div>

---

## 📋 Overview

XMeme is a simple backend service built with Spring Boot that powers a meme streaming page. Users can post memes with metadata (name, caption, image URL) and retrieve the latest 100 memes in real-time. The platform demonstrates modern Java best practices including REST API design, MongoDB integration, Docker containerization, and comprehensive testing.

## ✨ Features

- **RESTful API** - Well-designed REST endpoints for meme CRUD operations
- **MongoDB Integration** - Scalable NoSQL database for meme storage
- **Docker Support** - Complete containerization with Docker
- **API Documentation** - Auto-generated Swagger/OpenAPI documentation
- **Comprehensive Testing** - Unit and integration tests with JUnit 5 and Mockito
- **Code Quality** - Spotbugs, Checkstyle, and Jacoco integration
- **Error Handling** - Robust error response handling and validation
- **Build Automation** - Gradle-based build system with automated jar generation

## 🛠 Tech Stack

### Core Technologies
- **Language**: Java 11+
- **Framework**: Spring Boot 2.7.1
- **Database**: MongoDB 2.7.1
- **Build Tool**: Gradle 7.x
- **Container**: Docker

### Key Dependencies
- **Spring Data MongoDB** - Data persistence
- **Spring Web** - REST API development
- **Spring Validation** - Input validation
- **SpringDoc OpenAPI** - Swagger documentation
- **Lombok** - Boilerplate reduction
- **JUnit 5 & Mockito** - Testing framework
- **Spotbugs** - Static analysis
- **Checkstyle** - Code style enforcement
- **Jacoco** - Code coverage analysis

## 📦 Project Structure

```
ME_BUILDOUT_XMEME/
├── src/
│   ├── main/
│   │   ├── java/com/crio/starter/
│   │   │   ├── XmemeApplication.java      # Main application entry point
│   │   │   ├── controller/                # REST API controllers
│   │   │   ├── service/                   # Business logic layer
│   │   │   ├── repository/                # Data access layer
│   │   │   ├── data/                      # Entity models
│   │   │   ├── exception/                 # Custom exceptions
│   │   │   ├── exchange/                  # DTOs and request/response objects
│   │   │   └── handler/                   # Exception handlers
│   │   └── resources/
│   │       └── application.properties     # Configuration
│   └── test/                              # Unit and integration tests
├── sample-data/                           # Sample mongodb collection data
├── config/checkstyle/                     # Checkstyle rules
├── gradle/                                # Gradle wrapper
├── build.gradle                           # Gradle configuration
├── Dockerfile                             # Docker configuration
├── requirements.txt                       # Python dependencies
└── README.md                              # This file
```

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Gradle 7.x or higher (or use gradlew)
- Docker (for containerized setup)
- MongoDB (included in Docker setup)

### Local Development Setup

#### 1. Clone the Repository
```bash
git clone https://github.com/Anant-Saini/ME_BUILDOUT_XMEME.git
cd ME_BUILDOUT_XMEME
```

#### 2. Build the Project
```bash
./gradlew build
```

#### 3. Run Tests
```bash
./gradlew test
```

#### 4. Create Executable JAR
```bash
./gradlew bootJar
```

The JAR file will be located in `build/libs/`

### Docker Setup (Recommended)

#### Build Docker Image
```bash
docker build -t xmeme:latest .
```

This will start:
- MongoDB on port 27017
- XMeme application on port 8080

#### Run Docker Container Manually
```bash
docker run -p 8080:8080 xmeme:latest
```

### Verify Installation
Access the API documentation at:
```
http://localhost:8080/swagger-ui.html
```

## 📚 API Documentation

### Swagger UI
Once the application is running, access the interactive API documentation at:
- **URL**: `http://localhost:8080/swagger-ui.html`
- **API Docs JSON**: `http://localhost:8080/v3/api-docs`

### Available Endpoints

#### Create a Meme (POST)
```http
POST /api/memes
Content-Type: application/json

{
  "name": "John Doe",
  "caption": "When you finally understand Spring Boot",
  "url": "https://example.com/meme.jpg"
}
```

#### Get All Memes (GET)
```http
GET /api/memes
```
Returns the latest 100 memes

#### Get Meme by ID (GET)
```http
GET /api/memes/{id}
```

### Response Structure
```json
{
  "id": "60d5ec49c1234567890abc12",
  "name": "John Doe",
  "caption": "When you finally understand Spring Boot",
  "url": "https://example.com/meme.jpg"
}
```

## 🧪 Testing

### Run All Tests
```bash
./gradlew test
```

### Run Specific Test Class
```bash
./gradlew test --tests com.crio.starter.controller.MemeControllerTest
```

### Code Coverage Report
```bash
./gradlew jacocoTestReport
```
Generated report: `build/reports/jacoco/test/html/index.html`

### Code Quality Analysis
```bash
# Spotbugs
./gradlew spotbugsMain

# Checkstyle
./gradlew checkstyleMain
```

## 📋 Build & Deployment

### Build Configurations
- **Gradle**: Configured with custom build output directory
- **JAR Generation**: Automated jar file generation in build directories
- **Code Quality Plugins**: Spotbugs, Checkstyle, and Jacoco integrated

### Development Workflow
```bash
# Build and test
./gradlew clean build

# Run application locally (if not using Docker)
java -jar build/libs/xmeme-*.jar

# Or use Spring Boot Gradle plugin
./gradlew bootRun
```

## 🔧 Configuration

### Application Properties
Edit `src/main/resources/application.properties`:

```properties
# MongoDB Configuration
spring.data.mongodb.database=greetings
spring.data.mongodb.uri=mongodb://localhost:27017/greetings?authSource=admin

# Server Configuration
server.port=8080
server.servlet.context-path=/

# Application Name
spring.application.name=xmeme-api
```

### Docker Configuration

- MongoDB version
- Port mappings
- Environment variables
- Volume mounts

## 📁 Key Files

- **build.gradle** - Gradle build configuration with all dependencies
- **Dockerfile** - Docker image build instructions
- **src/main/java/com/crio/starter/XmemeApplication.java** - Spring Boot main class
- **sample-data/** - Sample JSON data for testing

## 🤝 Architecture

### Layered Architecture
```
Controller Layer (REST Endpoints)
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (Data Access)
        ↓
Database Layer (MongoDB)
```

### Key Components
- **Controllers**: Handle HTTP requests and responses
- **Services**: Implement business logic
- **Repositories**: Interface with MongoDB
- **Entities**: Domain models
- **DTOs**: Data transfer objects
- **Exception Handlers**: Centralized error handling

## 📝 License

This project is licensed under the **Apache License 2.0** - see the [LICENSE](LICENSE) file for details.

**Important**: When using this repository, it is mandatory to share the README.md and LICENSE file along with any changes you make to the contents.

## 🔗 Related Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [MongoDB Documentation](https://docs.mongodb.com/)
- [Docker Documentation](https://docs.docker.com/)
- [RESTful API Best Practices](https://restfulapi.net/)
- [Spring Data MongoDB Guide](https://spring.io/projects/spring-data-mongodb)

## 📧 Support

For issues, questions, or contributions, please feel free to open an issue or create a pull request.

---

<div align="center">

**Built with ❤️ using Spring Boot**

</div>
