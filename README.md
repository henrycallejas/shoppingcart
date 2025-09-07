# Shopping Cart API

Proyecto de ejemplo de un sistema de carrito de compras desarrollado con **Spring Boot**.

---

## Tabla de Contenidos

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Ejecución](#ejecución)
- [Autenticación y Seguridad](#autenticación-y-seguridad)
- [Documentación Swagger](#documentación-swagger)
- [Testing](#testing)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Autores](#autores)

---

## Descripción

API RESTful para la gestión de un carrito de compras, que incluye autenticación JWT, gestión de usuarios, productos, órdenes y clientes.

---

## Tecnologías

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security 6 (JWT)**
- **Swagger / OpenAPI (springdoc-openapi)**
- **Lombok**
- **MySQL** 
- **Maven**

---

## Requisitos Previos

- Java 17 o superior
- Maven 3.8+

---

## Instalación

1. Clona el repositorio:
    ```sh
    git clone https://github.com/henrycallejas/shoppingcart.git
    cd shoppingcart
    ```

2. Configura la base de datos en `src/main/resources/application.properties`.

3. Instala las dependencias:
    ```sh
    mvn clean install
    ```

---

## Ejecución

```sh
mvn spring-boot:run
```

---
## Autenticación y Seguridad

- **Login:**  
  Endpoint: `POST /login`  
  Body:
  ```json
  {
    "username": "admin",
    "password": "1234"
  }

## Documentación Swagger
Accede a la documentación interactiva en:
http://localhost:8080/swagger-ui.html

## Testing
Shopping_Cart usa Junit para tests unitarios. Ejecuta los test con:

Usando maven:

```sh
mvn test
```

---

## Estructura del Proyecto
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

## Autores
Henry Callejas
