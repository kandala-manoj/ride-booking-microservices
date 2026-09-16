# Ride Booking Microservices

A backend application for a **ride-booking system**, inspired by applications like Rapido.

This project is built using **Java, Spring Boot, Microservices, PostgreSQL, Redis, REST APIs, and JWT-based authentication**. The application is divided into independent services, with each service responsible for a specific business function.

## Project Objective

The objective of this project is to build a real-world ride-booking backend using a **microservices architecture**.

The system is designed to handle:

* Customer registration and management
* Rider and vehicle management
* Rider location management
* Finding nearby riders
* Ride booking
* User authentication
* Payment management
* Notification management
* Service-level exception handling
* Separate responsibilities across microservices

---

# Architecture

The application is divided into multiple Spring Boot microservices.


                         Client
                           |
                           v
                    +--------------+
                    | API Gateway  |
                    +--------------+
                           |
          +----------------+----------------+
          |                |                |
          v                v                v
   +-------------+  +-------------+  +-------------+
   |    Auth     |  |  Customer   |  |    Rider    |
   |   Service   |  |   Service   |  |   Service   |
   +-------------+  +-------------+  +-------------+
                                           |
                                           v
                                    +--------------+
                                    |   Location   |
                                    |    Service   |
                                    +--------------+
                                           |
                                           v
                                    +--------------+
                                    |    Redis     |
                                    |  GEO Data    |
                                    +--------------+

                           Booking Service
                                 |
                    +------------+------------+
                    |                         |
                    v                         v
             Payment Service          Notification
                                      Service


The services are designed to communicate through REST-based service interactions.

---

# Microservices

| Service              | Responsibility                                    |
| -------------------- | ------------------------------------------------- |
| Customer Service     | Customer registration and customer management     |
| Rider Service        | Rider and vehicle management                      |
| Location Service     | Rider location management and nearby-rider search |
| Booking Service      | Ride booking management                           |
| Auth Service         | Registration, login, security and JWT handling    |
| API Gateway          | Gateway layer and security configuration          |
| Payment Service      | Payment-related operations                        |
| Notification Service | Notification-related operations                   |

---

# Technologies Used

### Backend

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* REST APIs
* Maven

### Database

* PostgreSQL

### Caching and Location

* Redis
* Redis GEO

### Security

* Spring Security
* JWT

### Development Tools

* Eclipse
* Git
* GitHub
* Docker
* Postman

---

# Project Structure


Ride Booking-Microservices
│
├── authservice
│   ├── src/main/java
│   │   └── com/alpha/authservice
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── jwt
│   │       ├── repository
│   │       └── service
│   └── src/test
│
├── customerservice
│   ├── src/main/java
│   │   └── com/alpha/customerservice
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── repository
│   │       └── service
│   └── src/test
│
├── riderservice
│   ├── src/main/java
│   │   └── com/alpha/riderservice
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── repository
│   │       └── service
│   └── src/test
│
├── locationservice
│   ├── src/main/java
│   │   └── com/alpha/locationservice
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── exception
│   │       └── service
│   └── src/test
│
├── bookingservice
│   ├── src/main/java
│   │   └── com/alpha/bookingservice
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── repository
│   │       └── service
│   └── src/test
│
├── apigateway
│   ├── src/main/java
│   │   └── com/alpha/apigateway
│   │       ├── config
│   │       └── ApigatewayApplication.java
│   └── src/test
│
├── notificationservice
│   ├── src/main/java
│   │   └── com/alpha/notificationservice
│   │       ├── controller
│   │       └── NotificationserviceApplication.java
│   └── src/test
│
├── paymentservice
│   ├── src/main/java
│   │   └── com/alpha/paymentservice
│   │       ├── controller
│   │       ├── entity
│   │       ├── repository
│   │       └── service
│   └── src/test
│
├── .gitignore
└── README.md

---

# Service Details

## 1. Customer Service

The Customer Service is responsible for customer-related operations.

### Main components

* Customer Controller
* Customer Service
* Customer Repository
* Customer Entity
* Customer DTOs
* Exception handling

### Validations

The service contains handling for:

* Duplicate email
* Duplicate phone number
* Customer not found

---

## 2. Rider Service

The Rider Service manages riders and their vehicles.

### Main components

* Rider Controller
* Rider Service
* Rider Repository
* Rider Entity
* Vehicle Entity
* Rider DTOs
* Vehicle DTO
* Exception handling
* REST client configuration

### Validations

The service contains handling for:

* Duplicate email
* Duplicate phone number
* Duplicate license
* Rider not found

---

## 3. Location Service

The Location Service handles rider location-related operations.

Redis is used for location processing and nearby-rider searches.

### Main components

* Location Controller
* Location Service
* Redis Configuration
* Location DTOs
* Fare-related DTOs
* Exception handling

### Redis GEO

Rider location data can be processed using Redis GEO functionality.

Example Redis GEO key used by the project:


RIDERCURRENTLOCATION


Rider location members follow the project format:


riderId_VEHICLETYPE


Example:


104_BIKE


This allows nearby riders to be searched based on geographic location and vehicle type.

---

# 4. Booking Service

The Booking Service manages ride booking operations.

### Main components

* Booking Controller
* Booking Service
* Booking Repository
* Booking Entity
* Booking DTOs
* Payment Request DTO
* Redis Configuration
* Exception handling

The booking flow can communicate with the Location Service to find nearby riders.

Example service communication:


Booking Service
       |
       v
Location Service
       |
       v
Redis
       |
       v
Nearby Riders


---

# 5. Authentication Service

The Authentication Service handles user authentication and security-related operations.

### Main components

* Auth Controller
* Auth Service
* User Entity
* User Repository
* Login DTO
* Register DTO
* JWT Service
* Security Configuration

JWT is used as part of the authentication implementation.

Basic authentication flow:


User
 |
 | Register / Login
 v
Auth Service
 |
 v
JWT
 |
 v
Authenticated Request


---

# 6. API Gateway

The API Gateway provides a dedicated gateway layer for the application.

### Main components

* API Gateway Application
* Security Configuration

The gateway is intended to provide a centralized entry point and security layer for backend services.

---

# 7. Payment Service

The Payment Service provides a separate service for payment-related operations.

### Main components

* Payment Controller
* Payment Service
* Payment Entity
* Payment Repository

The service is separated from the Booking Service so payment-related responsibilities can be developed independently.

---

# 8. Notification Service

The Notification Service provides a separate service for notification-related operations.

### Main components

* Notification Controller
* Notification Service Application

The service structure allows notification functionality to be extended independently in the future.

---

# Database

PostgreSQL is used for persistent application data.

The microservices are organized so that business responsibilities are separated across services.

Typical service data includes:


Customer Service
      |
      v
Customer Data

Rider Service
      |
      v
Rider + Vehicle Data

Booking Service
      |
      v
Booking Data

Payment Service
      |
      v
Payment Data

Auth Service
      |
      v
User Data


---

# Redis

Redis is used for location-related processing.

The project uses Redis GEO functionality for geographic rider searches.

Example:


Redis
 |
 +-- RIDERCURRENTLOCATION
       |
       +-- 101_BIKE
       +-- 102_BIKE
       +-- 103_AUTO
       +-- 104_BIKE


A nearby-rider search can use:

* Latitude
* Longitude
* Search radius
* Vehicle type

---

# Exception Handling

The services contain dedicated exception handling.

Examples include:


CustomerNotFoundException
EmailAlreadyExistsException
PhoneAlreadyExistsException

RiderNotFoundException
LicenseAlreadyExistsException

BookingNotFoundException
BookingNotAvailableException
BookingAlreadyAcceptedException

LocationNotFoundException
InvalidSearchKeyException

LocationServiceException


Global exception handlers are used where required to provide centralized exception handling within individual services.

---

# Testing

Each major service contains a Spring Boot test structure.

Test classes are available for:


Customer Service
Rider Service
Location Service
Booking Service
Auth Service
API Gateway
Notification Service
Payment Service

The project uses the Spring Boot testing setup provided by each service.

---

# How to Run the Project

## Prerequisites

Install the following:

* Java 21
* Maven
* PostgreSQL
* Redis
* Docker
* Git
* Eclipse or another Java IDE
* Postman for API testing

---

## 1. Clone the Repository

bash
git clone https://github.com/kandala-manoj/ride-booking-microservices.git

Go to the project directory:

bash
cd ride-booking-microservices


---

## 2. Start PostgreSQL

Make sure PostgreSQL is running.

Create/configure the databases required by the individual services according to their application.properties files.

The database configuration is available inside:


<service>/src/main/resources/application.properties


---

## 3. Start Redis

If using Docker, Redis Stack can be started with:

```bash
docker start redis-stack
```

Check the running containers:

```bash
docker ps
```

Redis should be available on:


localhost:6379

---

## 4. Configure Application Properties

Before starting each service, verify its:


src/main/resources/application.properties


Check:

* Database URL
* Database username
* Database password
* Server port
* Redis configuration
* Other service-specific configuration

Do not commit passwords or other sensitive credentials to GitHub.

---

# Running Individual Services

Each microservice is an independent Spring Boot application.

From a service directory, run:

bash
mvn spring-boot:run


For example:

bash
cd customerservice
mvn spring-boot:run


Similarly:

authservice
customerservice
riderservice
locationservice
bookingservice
apigateway
notificationservice
paymentservice


The services can also be started directly from Eclipse as Spring Boot applications.

---

# API Testing

Postman can be used to test the REST APIs.

Typical testing flow:


1. Register User
       ↓
2. Login
       ↓
3. Get Authentication Token
       ↓
4. Register Customer / Rider
       ↓
5. Add Rider Location
       ↓
6. Search Nearby Riders
       ↓
7. Create Booking
       ↓
8. Process Payment
       ↓
9. Notification Flow


The exact API endpoints and request formats are available in the controller classes of each service.



# Example Technology Flow


Client
  |
  v
API Gateway
  |
  +------> Auth Service
  |
  +------> Customer Service ------> PostgreSQL
  |
  +------> Rider Service ---------> PostgreSQL
  |
  +------> Booking Service -------> PostgreSQL
  |              |
  |              v
  |        Location Service
  |              |
  |              v
  |           Redis
  |
  +------> Payment Service --------> PostgreSQL
  |
  +------> Notification Service


---

# Project Highlights

* Built using **Java and Spring Boot**
* Microservices-based backend architecture
* Separate services for major business responsibilities
* REST API based communication
* PostgreSQL for persistent data
* Redis for location processing
* Redis GEO for nearby-rider search
* JWT-based authentication implementation
* Spring Security configuration
* Service-level exception handling
* Repository and service layer separation
* DTO-based request/response handling
* Dedicated payment and notification services
* Spring Boot test structure for individual services
* Git-based version control

---

# Future Enhancements

The following features can be added to extend the project:

* Complete API Gateway routing
* Kafka-based event-driven communication
* Real-time ride status updates
* Real-time notifications
* External payment gateway integration
* Service discovery
* Centralized configuration
* Docker Compose for all services
* API documentation using Swagger/OpenAPI
* Distributed tracing
* Centralized logging
* Monitoring and health checks
* CI/CD pipeline
* Cloud deployment

---

# Learning Outcomes

Through this project, the following concepts were practiced:

* Java backend development
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* REST APIs
* Microservices architecture
* PostgreSQL
* Redis
* Redis GEO
* JWT authentication
* Spring Security
* DTO and entity separation
* Repository and service layers
* Exception handling
* Unit/test setup
* Git and GitHub

---

# Author

**Kandala Manoj**

Java Developer | Spring Boot | Microservices | Full Stack Development

GitHub:
https://github.com/kandala-manoj

LinkedIn:
https://linkedin.com/in/kandala-manoj-a6b69333b

---

# Repository

GitHub Repository:

https://github.com/kandala-manoj/ride-booking-microservices

---

## License

This project is developed for **learning, portfolio, and educational purposes**.
