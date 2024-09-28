# Payments Service

## Description
This microservice manages payment processing, allowing clients to process payments and check payment status.

## Features
- Process a new payment
- Retrieve payment status by ID

## API Endpoints

| Method | Endpoint                   | Description                      |
|--------|----------------------------|----------------------------------|
| POST   | `/api/v1/payments/process` | Process a new payment            |
| GET    | `/api/v1/payments/{id}`    | Retrieve payment status by ID    |

## Models

- **PaymentRequestDto**: Represents the payment request.
- **PaymentResponseDto**: Represents the response for a payment status.

## Technologies Used
- Spring Boot
- Java
- JPA/Hibernate
- Swagger/OpenAPI

## Installation
1. Clone the repository.
2. Run `mvn clean install` to build the project.
3. Configure your application properties.
4. Run the application using your IDE or with `mvn spring-boot:run`.

## License
This project is licensed under the License Apache 2.0.
