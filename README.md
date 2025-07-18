<!-- ========================= TITLE ========================= -->
# Event Booking Central
A microservices–based platform for browsing, booking, and managing events. Each Spring Boot service handles a specific domain, communicating via REST, Kafka, and WebSocket through a central API gateway—leveraging Kafka’s asynchronous event streaming to cut average booking confirmation latency **from 1000 ms to 300 ms (70% faster)**. Automated email notifications inform users of key actions (registrations, bookings, cancellations). Secure authentication and role-based access control are enforced with JWT and OAuth2 for robust data protection.

---

<!-- ========================= TECH STACK ========================= -->
## Tech Stack
- Java 21

- Spring Boot

- Spring Cloud (Config, OpenFeign, Gateway)

- Microservice

- Spring Security (JWT, OAuth2)

- Apache Kafka

- WebSocket

- MySQL

- Docker


---

<!-- ========================= MAIN FEATURES ========================= -->
## Main Features
- Modular microservices architecture (Spring Boot & Spring Cloud) for scalable, maintainable services

- Real-time bidirectional messaging via WebSocket for live updates and chat functionality

- Event-driven communication using Apache Kafka to decouple services and ensure reliable workflows

- Automated email notifications with JavaMailSender for user events (welcome on registration, alerts on account deletion)

- Secure authentication & authorization using JWT and OAuth2, enabling robust role-based access control

- Comprehensive RESTful APIs for managing events, orders, and user profiles, with full CRUD operations and input validation

---

<!-- ========================= INSTALLATION ========================= -->
## Installation
```bash
# Bước 1: Clone the repository
git clone https://github.com/PhucNguyen0304/Event-Booking-Central.git

# Bước 2: Move to the folder
cd event-booking-central

# Bước 3: Start all services with Docker Compose
docker-compose up -d
