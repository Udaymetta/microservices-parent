# 🧩 Microservices Parent Project
This repository serves as the parent project for a suite of microservices developed using Spring Boot and Spring Cloud. It demonstrates a modular architecture, facilitating scalability and maintainability.

# 📁 Project Structure

| Module/Service         | Description                                                    |
| ---------------------- | -------------------------------------------------------------- |
| `api-gateway`          | Centralized API gateway for routing requests.                  |
| `framework-lib`        | Shared library containing common utilities and configurations. |
| `inventory-service`    | Manages inventory data and operations.                         |
| `notification-service` | Handles notifications and messaging.                           |
| `order-service`        | Processes and manages customer orders.                         |
| `product-service`      | Manages product information and operations.                    |
| `prometheus`           | Monitoring setup using Prometheus.                             |
| `service-registry`     | Eureka-based service registry for service discovery.           |


# 🚀 Technologies Used
Java 17

Spring Boot 3

Spring Cloud (Eureka, Gateway)

Spring Data JPA

Docker & Docker Compose

Prometheus & Grafana for monitoring

🛠️ Getting Started
Prerequisites
Java 17

Maven

Docker & Docker Compose

# Steps to Run
Clone the repository:

git clone https://github.com/Udaymetta/microservices-parent.git

cd microservices-parent

Start the services using Docker Compose:

docker-compose up --build

This will build and start all the microservices along with Prometheus and Grafana for monitoring.

Access the services:

API Gateway: http://localhost:8080

Eureka Server: http://localhost:8761

Prometheus: http://localhost:9090

Grafana: http://localhost:3000

# 📊 Monitoring
Prometheus is configured to scrape metrics from the microservices.

Grafana dashboards are set up to visualize the metrics.

# 📌 Future Enhancements
Implement centralized configuration using Spring Cloud Config.

Add security using Spring Security and OAuth2.
