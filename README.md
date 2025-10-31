# 📚 Managing Books RESTful API

A simple RESTful API built using **Spring Boot (Java)**, **JDBI**, and **MySQL**  
to manage a list of books with author search and validation.

---

## 🧩 Features

- **GET /books?author={authorName}**
    - Returns all books written by the given author.
    - Optimized with a case-insensitive index on author (avoids full table scan).
- **POST /books**
    - Accepts a JSON body to create a new book.
    - Supports **Buddhist calendar dates (พ.ศ.)** and automatically converts to Gregorian year (ค.ศ.).
- Validations:
    - `title` and `author` must not be empty.
    - `publishedDate` must be valid and within a reasonable year range (>1000 and <= current year).

---

## 🧱 Tech Stack

- Java 17 (compatible with Java 11+)
- Spring Boot
- JDBI 3
- MySQL (via Docker)
- Maven
- JUnit 5 (Integration Tests)

---

## 🗄️ Database Setup

### 🐳 Run MySQL using Docker
```
docker run --name mysql-books \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=booksdb \
  -p 3306:3306 \
  -d mysql:8
```  

### 🧩 (Optional) Manual schema creation
Connect to MySQL and run:
```sql
CREATE DATABASE IF NOT EXISTS booksdb;
USE booksdb;

CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_date DATE NOT NULL,
    INDEX idx_author_lower ((LOWER(author)))
);
```
---

### ⚙️ Application Configuration
Edit your src/main/resources/application.properties:
```properties
spring.application.name=ManagingBooks
spring.datasource.url=jdbc:mysql://localhost:3306/booksdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Bangkok
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
---

## 🚀 Getting Started
▶️ Running the Server
```
mvn spring-boot:run
```

Once the server starts, access the API at:

👉 http://localhost:8080/books

---

## 🔍 API Examples

✅ GET /books?author=John Doe
```
Request:

GET http://localhost:8080/books?author=John Doe
```
```
Response:

[
  {
    "id": 1,
    "title": "Book One",
    "author": "John Doe",
    "publishedDate": "2023-01-01"
  }
]
```
✅ POST /books
```
Request:

POST http://localhost:8080/books
Content-Type: application/json
```
```
Body:

{
  "title": "New Book",
  "author": "Jane Smith",
  "publishedDate": "2567-02-10"
}
```
```
Response:

{
  "id": 4,
  "title": "New Book",
  "author": "Jane Smith",
  "publishedDate": "2024-02-10"
}
```

🧠 Note: Buddhist year (พ.ศ.) automatically converts to Gregorian year (ค.ศ.)

---

## 🧪 Integration Tests

Integration tests verify that the REST API and database work end-to-end.
```
Test file:

src/test/java/com/example/ManagingBooks/BookControllerIntegrationTest.java
```
```
Run tests:

mvn test
```

Tested Scenarios:

✅ POST /books — Create a new book

✅ GET /books?author={author} — Fetch by author

---

## 👩🏻‍💻 Author
Developed by Mint2537N 🌸
“Built with love and logic.”

---
