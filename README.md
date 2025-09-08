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
2. Just create a database in mysql

3. Configure the database in `src/main/resources/application.properties`.

   Configure your username and passwor for mysql:
   ```sh
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME?useSSL=false&serverTimezone=UTC
    ```

5. If this is the first time you run the project, use this configuration (By Default):
```sh
spring.jpa.hibernate.ddl-auto=create-drop
 ```
With this configuration, the necessary tables for the database will be created.

5. Install dependencies:
    ```sh
    mvn clean install
    ```

---

## Running

```sh
mvn spring-boot:run
```

Important: Once the project is up and running, change the Hibernate configuration so that your data is not deleted when you restart the project:
```sh
spring.jpa.hibernate.ddl-auto=update
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
