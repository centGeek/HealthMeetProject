# HealthMeet Medical Application Project

## Project Description

HealthMeet is a Java-based medical application project that utilizes two main architectural patterns: Spring Web MVC and REST API.

## Application Features

The application offers a variety of features that make it easy for both patients and doctors to use the system. Here are the main functions available to both user groups:

### Doctors

- **Availability**: Doctors can specify their availability for potential appointments, allowing patients to schedule appointments at chosen times.
- **Registration**: New doctors need to register in the application to start using its services.
- **Medical Notes**: After a patient visit, a doctor can enter medical notes that are stored in the system.
- **Medical History**: Doctors have access to a patient's medical history, making diagnosis and treatment easier.
- **Prescriptions**: Doctors can issue prescriptions and assign medications for a given visit. They enter the quantity of medications, their total cost, and update patient information. Doctors can also update their profiles.

### Patients

- **Appointment Booking**: Patients can schedule appointments with selected doctors during available time slots. The application handles cases where two patients try to book the same slot with the same doctor.
- **Registration**: New patients also need to register to use the application.
- **Visit History**: Patients can check the history of past visits and upcoming appointments.
- **Appointment Cancellation**: Patients have the option to cancel previously scheduled appointments.
- **Medical Notes**: Patients can review medical notes written by doctors for past visits.
- **Prescriptions**: Doctors can issue prescriptions and assign medications for a given visit. Patients enter the quantity of medications and their total cost. Patients can also update their profiles.

## Default Users

To facilitate application testing, default users are provided:

### Doctors

1. Email: tom.shelby@medi.com, Password: test
2. Email: grace.shelby@medi.com, Password: test
3. Email: krzysztof.cool@medi.com, Password: test

### Patients

1. Email: w.white@gmail.com, Password: test
2. Email: s.white@gmail.com, Password: test
3. Email: j.pinkman@wp.pl, Password: test

## Technical Aspects

Tests are run using **testContainers**. Docker must be installed on your operating system. You can also run the application in Docker by default on port 8081.

### Testing

As part of the project, various types of tests have been conducted, including:

- **Unit Test**: Tests that focus on isolating and testing individual components of the system, such as service and controller methods.
- **Parametrized Tests**: Tests with different parameter sets. We test the validation process in our application.
- **WebMvcTest**: Tests for the Spring MVC controller layer.
- **RestAssured Tests**: REST API tests using the RestAssured library.
- **Data JPA Tests**: Integration tests with the database using Spring Data JPA.
- **Spring Boot Application Test**: Tests for the entire Spring Boot application.

### Database Migrations

To initialize tables and data in the database, we use Flyway migrations. Migrations allow us to manage the database schema and evolve its structure.

## Installation Instructions

1. Clone the repository to your local computer:

   ```bash
   git clone https://github.com/centGeek/HealthMeetProject
   ```

2. Navigate to the project directory:

   ```bash
   cd PathToThisFile
   ```

3. Configure database access and other settings in the `application.properties` file.

4. Run the application by clicking on the `HealthyMeetApplication.java` file, and then visit the website at `http://localhost:8080/HealthMeet` by default.

## Application Operation
1. During user registration, make sure to validate the provided data. The input arguments must follow these patterns:
   **Phone** -> **+xx xxx xxx xxx**, where spaces are required, and the field must be unique.
   **Email** -> Must have the structure of an email, although it doesn't need to be an actual email address.
   **Nickname** -> Minimum of **5** characters in length.

2. During login, use your **email** and **password**.

## Contact

If you are interested in my project, feel free to reach out to me at the email address [centkowski.lukasz03@gmail.com].
