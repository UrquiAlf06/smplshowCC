
# User Management Service

simpleshow Coding Challenge based on User Management API for Backend Development featuring user creation, information retrieval and unit testing on Spring Boot.


## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Building and Running the Application](#building-and-running-the-application)
- [Running Tests](#running-tests)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
---

## Project Overview
This project provides a RESTful API to manage users and their roles, allowing operations such as:
- Adding new users with their basic information such as complete names, email, password and role.
- Retrieving user information by ID and all users with their names.
- Managing user roles.

## Technologies Used
- **Java**: Main programming language
- **Spring Boot**: Application framework
- **Spring Data JPA**: Data persistence
- **Hibernate**: ORM framework
- **MySQL**: Databased used
- **Maven**: Dependency management and build automation
- **Mockito and JUnit**: Unit testing framework

## Getting Started
To set up and run this project on your local machine, follow these instructions.

### Prerequisites
- **Java 17** or higher
- **Maven** (latest version recommended)

### Cloning the Repository
```bash
git clone https://github.com/your-username/user-management-service.git
cd user-management-service
```

### Configuration
The application uses the `application.properties` file located in `src/main/resources` for database and other configurations. By default, it is configured to use a MySQL database, with settings for database creation, JPA properties, and API endpoint prefixing.

**Sample `application.properties` configuration:**
```properties
# Application settings
spring.application.name=usermgmt
server.port=8000

# MySQL Database configuration
spring.datasource.url=jdbc:mysql://localhost:8000/user_mgmt?createDatabaseIfNotExist=true
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA and Hibernate settings
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update

# Database initialization
spring.datasource.data=classpath:data.sql
spring.datasource.initialization-mode=always

# API Endpoint prefix
api.prefix=/api/v1
```

## Database initialization

The scripts under `src/main/resources/data.sql` are for creating records in the DB regarding Users and Roles for the users.

## Building and Running the Application
To build the project, navigate to the project directory and run:
```bash
mvn clean install
```

To start the application:
```bash
mvn spring-boot:run
```

The application will run on `http://localhost:8080`.

## Running Tests
Unit tests are located in the `src/test/java` directory and can be run with:
```bash
mvn test
```

## Main API Endpoint: 

http://localhost:8000/api/v1

### User Endpoints
- **POST /users**: Create a new user

### Example JSON Payload for Creating a User
```json
{
    "firstName": "Alfonso",
    "lastName": "Urquia",
    "email": "alfonso.urquia@simpleshow.com",
    "password": "password",
    "roleId": 1
}
```
### Example JSON Response for Creating a User Succesfully
```json
{
    "message": "User created!",
    "data": {
        "firstName": "Miguel",
        "lastName": "Urq",
        "email": "alf@gmail.com"
    }
}
```
### Example JSON Response for Email Already Existing
```json
{
    "message": "alf@gmail.com this TOP G already exists",
    "data": null
}
```
### Example JSON Response for Missing a Required Value
```json
{
    "message": "Role is mandatory",
    "data": null
}
```

- **GET /users/{id}**: Retrieve User information by its ID

### Example JSON Response for Retrieving a User for its ID
```json
{
    "message": "User by Id: 1",
    "data": {
        "firstName": "Miguel",
        "lastName": "Urq",
        "email": "alf@gmail.com"
    }
}
```
### Example JSON Response for Not finding an user by its Id
```json
{
    "message": "User with Id: 9 is not found!",
    "data": null
}
```

- **GET users/getAllByTheirName?firstName=**: Retrieve users that match a certain pattern in their first names. This endpoint performs a case-insensitive search for users whose first names contain the specified substring, allowing for partial matches. For example, querying ?firstName=onso would return users like "Alfonso."

### Example JSON Response for Retrieving a User for its ID
```json
{
    "message": "Users by first name containing: miguel",
    "data": [
        {
            "firstName": "Miguel",
            "lastName": "Urq",
            "email": "alf@gmail.com"
        }
    ]
}
```
### Example JSON Response for Not Retrieving a User for its ID
```json
{
    "message": "No users found with first name containing: alfo",
    "data": null
}

```


## Project Structure
```plaintext
src
├── main
│   ├── java
│   │   └── com
│   │       └── au
│   │           └── usermgmt
│   │               ├── config      # Contains configuration class for application settings
│   │               ├── controller  # REST controllers that handle incoming HTTP requests and return responses.
│   │               ├── dto         # Contains Data Transfer Objects used for transferring data between layers of the application.
│   │               ├── exceptions  # Custom exception classes for error handling throughout the application.
│   │               ├── model       # Spring Data JPA repositories that defines the entity classes representing the data model and database tables.
│   │               ├── repository  # Contains interfaces for data access, extending Spring Data JPA repositories for CRUD operations.
│   │               └── request     # Classes representing the structure of incoming requests from clients.
│   │               └── response    # Classes that structure the outgoing responses to clients.
│   │               └── service     # Implements business logic and interacts with repositories to manage application data.
│   └── resources
│       ├── application.properties  # Application configuration
│       └── data.sql                # Sample data for database initialization
└── test
    └── java
        └── com
            └── au
                └── usermgmt
                    └── UserServiceTests  # Unit tests for service layer
```