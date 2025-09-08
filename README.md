# Shopping Cart API

Sample project for a shopping cart system developed with **Spring Boot**.

---

## Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running](#running)
- [Authentication and Security](#authentication-and-security)
- [Swagger Documentation](#swagger-documentation)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Authors](#authors)

---

## Description

RESTful API for managing a shopping cart, including JWT authentication, user management, products, orders, and clients.

---

## Technologies

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security 6 (JWT)**
- **Swagger / OpenAPI (springdoc-openapi)**
- **Lombok**
- **MySQL**
- **Maven**

---

## Prerequisites

- Java 17 or higher
- Maven 3.8+

---

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/henrycallejas/shoppingcart.git
    cd shoppingcart
    ```

2. Configure the database in `src/main/resources/application.properties`.

3. Install dependencies:
    ```sh
    mvn clean install
    ```

---

## Running

```sh
mvn spring-boot:run
```

---
## Authentication and Security

- **Login:**  
Endpoint: POST /login
Body:
  ```json
  {
    "username": "admin",
    "password": "1234"
  }

## Swagger Documentation
Access the interactive documentation at:
http://localhost:8080/swagger-ui.html

## Testing
Shopping_Cart uses JUnit for unit tests. Run the tests with:

Using Maven:

```sh
mvn test
```

---

## Project Structure
```sh
src/
 └── main/
     ├── java/
     │    └── com/
     │         └── shoppingcart/
     │              ├── app/
     │              │    ├── controllers/
     │              │    ├── dto/
     │              │    ├── entities/
     │              │    ├── repositories/
     │              │    ├── security/
     │              │    ├── services/
     │              │    └── utils/
     └── resources/
          └── application.properties
```

---

## Author
Henry Callejas
