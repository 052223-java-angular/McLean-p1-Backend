# P1 - The Planetary Full Stack Application

## Introduction

The planetary application will primarily be built using Angular framework for frontend and Spring Boot framework for backend. The full stack application will be built on the foundation of Java, HTML, CSS and JavaScript in conjuction with TypeScript and will utilize a PostgreSQL database to store user information, preferences and comments. The aim of the application is to provide the user with quality planetary information all in one place (a difficult task).

## User Stories

- **As a user**, I want to register an account so that I can have a personalized learning experience.
- **As a user**, I want to log in to my account so that I can access my personal notes and saved data.
- **As a user**, I want to view current sky conditions for my location.
- **As a user**, I want to view data about each planet such as current constellation.  
- **As a user**, I want to store geolocation data for preferences and study.
- **As a user**, I want to save important dates to review at a later time.
- **As a user**, I want to add favorites attributed to planets for note keeping and tracking thoughts.
- **As a user**, I want to create comments available for view by all users.
- **As a user**, I want to check out and pay for my subscription securely so that my personal and financial information is safe.
- **As a user**, I want to review my subscription history so that I can keep track of my monthly payments.


## MVP (Minimum Viable Product)

- Secure user registration and login
- Storing geolocation data with ability to change home location
- View current sky conditions per location
- View current data and info about each planet
- Saving important dates to review
- Adding favorites with notes
- Comment section
- Secure checkout
- Subscription history

## Stretch Goals

- Implementing an algorithm to sort comments based on date and relevancy to user
- Adding an admin role that can add, remove, or modify comments
- Implementing constellation system to provide user with conjunction information

## Tech Stacks

- **Public API**: An API will be used to generate data for the user at the frontend.
- **Angular**: The main framework used for building the application's frontend.
- **Spring Boot**: The framework used for building the application's backend.
- **TypeScript**: A framework built on JavaScript used for building the application's frontend.
- **JavaScript**: The programming language used to add dynamic features on the application's frontend.
- **CSS**: The programming language used for adding style to the application's frontend.
- **HTML**: The markup language used for building the application's frontend.
- **Java**: The foundational programming language used for building the application's backend.
- **PostgreSQL**: Used as the database to store user, product, and order data.
- **Maven or Gradle**: Used for managing project dependencies.
- **Log4j**: A logging utility for debugging purposes.
- **JDBC (Java Database Connectivity)**: An API for connecting and executing queries on the database.
- **BCrypt**: A Java library for hashing and checking passwords for security.
- **JUnit, Mockito, and PowerMock**: Used for unit and integration testing.
- **Git and GitHub**: Used for version control.

## Requirements

- **Clean Codebase**: All code should be clean and well-documented. The repository should not include any unnecessary files or folders such as the `target/`, `.DS_Store`, etc. All files and directories should be appropriately named and organized.

- **Database Design**: The database should be designed following the principles of the 3rd Normal Form (3NF) to ensure data integrity and efficiency. An Entity Relationship Diagram (ERD) should be included in the documentation.

- **Secure**: All sensitive user data such as passwords must be securely hashed before storing it in the database. The application should not display any sensitive information in error messages.

- **Error Handling**: The application should handle potential errors gracefully and provide clear and helpful error messages to the users.

- **Testing**: The application should have a high test coverage. Unit tests and integration tests should be implemented using JUnit, Mockito, and PowerMock.

- **Version Control**: The application should be developed using a version control system, preferably Git, with regular commits denoting progress.

- **Documentation**: The repository should include a README file with clear instructions on how to run the application. Code should be well-commented to allow for easy understanding and maintenance.

- **Scalable**: The design of the application should be scalable, allowing for easy addition of new features or modifications in the future.

## Entity Relationship Diagram

![image](https://github.com/052223-java-angular/McLean-p1-Backend/assets/123115057/fd27fd46-3d6b-43c9-9c75-f0f4419ddb3b)

