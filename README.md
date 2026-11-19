# OOPJ MSE-2 | Java Projects

A collection of Java projects developed as part of the **Object-Oriented Programming with Java (CS336B)** Mid-Semester Examination 2 at KIET.

This repository contains two practical implementations demonstrating Java programming, database connectivity, SQL operations, and REST API development using Spring Boot.

## 📂 Repository Structure

```text
oops-with-java-mse-2/
│
├── Question 1/
│   ├── lib/
│   │   └── mysql-connector-j.jar
│   ├── src/
│   │   ├── Order.java
│   │   └── OrderManagement.java
│   └── out/
│
├── Question 2/
│   └── Q2-Library-Management/
│       ├── src/
│       │   └── main/
│       │       ├── java/com/example/library/
│       │       │   ├── controller/
│       │       │   │   └── BookController.java
│       │       │   ├── model/
│       │       │   │   └── Book.java
│       │       │   ├── service/
│       │       │   │   └── BookService.java
│       │       │   └── LibraryApplication.java
│       │       └── resources/
│       │           └── application.properties
│       ├── pom.xml
│       └── mvnw.cmd
│
└── README.md
```

---

## 1. Order Management System | Java JDBC & MySQL

A menu-driven Java application that manages customer orders using JDBC and a MySQL database.

### Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- PreparedStatement

### Features

| Operation | Description |
|---|---|
| Insert Orders | Inserts at least three orders and handles duplicate IDs. |
| Search Orders | Filters orders by status and quantity greater than 2, sorted by unit price in descending order. |
| Update Quantity | Increases the quantity of an existing order. |
| Update Status | Validates and updates order status. |
| Total Order Value | Calculates total order value using SQL `SUM()`. |
| Conditional Delete | Deletes an order only when its status is `Cancelled`. |

### Database Configuration

Create the database and table in MySQL:

```sql
CREATE DATABASE ecommerce_db;
USE ecommerce_db;

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    product_name VARCHAR(100),
    quantity INT,
    unit_price DOUBLE,
    status VARCHAR(30)
);
```

Configure your MySQL credentials in `OrderManagement.java`:

```java
static final String URL =
    "jdbc:mysql://localhost:3306/ecommerce_db";

static final String USER = "root";
static final String PASSWORD = "YOUR_PASSWORD";
```

### How to Run

Make sure Java, MySQL, and MySQL Connector/J are installed.

Open the terminal inside the `Question 1` directory.

**Compile:**

```bash
javac -cp "lib/mysql-connector-j.jar" -d out src/*.java
```

**Run on Windows:**

```bash
java -cp "out;lib/mysql-connector-j.jar" OrderManagement
```

The application provides a menu to perform all order management operations.

---

## 2. Library Management REST API | Spring Boot

A RESTful API built using Spring Boot to perform CRUD (Create, Read, Update, Delete) operations on library book records.

The application uses an **ArrayList** for in-memory data storage and follows a layered architecture consisting of Model, Service, and Controller.

### Technologies Used

- Java
- Spring Boot
- Spring Web
- Maven
- REST API
- Postman
- ArrayList

### Book Model

Each book contains the following attributes:

| Field | Data Type |
|---|---|
| id | Integer |
| title | String |
| author | String |
| category | String |
| availableCopies | Integer |

### API Endpoints

Base URL: `http://localhost:8081/books`

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/books` | Add a new book |
| GET | `/books` | Retrieve all books |
| GET | `/books/{id}` | Retrieve a book by ID |
| PUT | `/books/{id}` | Update book details |
| DELETE | `/books/{id}` | Delete a book by ID |

### Sample JSON Request

Use this JSON body with the POST endpoint:

```json
{
  "id": 101,
  "title": "The Complete Java Reference",
  "author": "Herbert Schildt",
  "category": "Programming",
  "availableCopies": 10
}
```

### HTTP Response Handling

- `201 Created` – Book added successfully.
- `200 OK` – Book retrieved or updated successfully.
- `204 No Content` – Book deleted successfully.
- `404 Not Found` – Requested book does not exist.
- `409 Conflict` – Duplicate book ID.

### How to Run

Navigate to the Spring Boot project directory:

```bash
cd "Question 2/Q2-Library-Management"
```

Run the application on Windows:

```bash
.\mvnw.cmd spring-boot:run
```

Alternatively, run `LibraryApplication.java` directly from your IDE.

The API will be available at:

```text
http://localhost:8081
```

Use Postman to test the available REST endpoints.

> **Note:** Book records are stored in an in-memory ArrayList. Therefore, the data resets whenever the application restarts.

---

## 📸 Project Demonstrations

The project documentation includes:

- Java source code for both applications.
- MySQL database and terminal execution screenshots.
- Spring Boot application structure.
- Postman API testing screenshots.
- Three AI prompts for each project.

## 🎯 Learning Outcomes

Through these projects, I gained practical experience with:

- Object-Oriented Programming concepts in Java.
- JDBC connectivity and database operations.
- SQL queries, filtering, sorting, and aggregate functions.
- Exception handling and PreparedStatement.
- Building RESTful APIs using Spring Boot.
- HTTP methods and status codes.
- Layered application architecture.
- Testing REST APIs using Postman.

## 👨‍💻 Author

**Veer Vardhan Singh**

B.Tech – Information Technology  
KIET Deemed to be University  
Batch: 2025–2029

---

*Developed as part of the OOPJ (CS336B) MSE-2 practical examination.*
