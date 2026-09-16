\# Ride Booking Microservices



A backend application for a \*\*ride-booking system\*\*, inspired by applications like Rapido.



The project is developed using \*\*Java and Spring Boot Microservices\*\*. It is divided into separate services for customers, riders, locations, bookings, authentication, payments, and notifications.



The main goal of this project is to understand and implement a \*\*real-world microservices-based backend architecture\*\* using REST APIs, PostgreSQL, Redis, and JWT authentication.



\## Project Objective



The system is designed to support the basic flow of a ride-booking application:



\* Customer registration and management

\* Rider and vehicle management

\* Rider location management

\* Finding nearby riders

\* Creating and managing bookings

\* User authentication

\* Payment management

\* Notification management



The project follows a \*\*service-based architecture\*\*, where each major business responsibility is handled by a separate Spring Boot service.



\## Key Features



\### Customer Management



\* Customer registration

\* Customer details management

\* Email and phone validation

\* Customer-specific service layer and repository



\### Rider Management



\* Rider registration

\* Rider and vehicle management

\* License validation

\* Email and phone validation

\* Rider-specific database access



\### Location Management



\* Store and manage rider locations

\* Redis-based location handling

\* Find nearby riders based on location

\* Support for vehicle type filtering

\* Redis GEO-based location processing



\### Booking Management



\* Create ride booking requests

\* Store booking details

\* Connect booking flow with nearby rider search

\* Booking status and exception handling

\* Integration with the location service



\### Authentication



\* User registration and login

\* JWT-based authentication

\* Authentication service

\* Security configuration



\### Payment Management



\* Payment entity and repository

\* Payment service layer

\* Payment REST controller

\* Payment-related booking support



\### Notification Management



\* Dedicated notification microservice

\* Notification REST controller

\* Separate service structure for future notification integrations



\### API Gateway



\* Dedicated API Gateway service

\* Centralized security configuration

\* Designed as the entry point for communication between clients and backend services



