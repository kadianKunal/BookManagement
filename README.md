# BookManagement

![Book Store](https://github.com/kadianKunal/BookManagement/assets/35004605/58aff5a2-1c83-455b-8d16-3464b249e3ff)


The Book management Service is a microservice which is part of Online Bookstore.
It has RESTful API for managing books inventory. It provides endpoints to perform CRUD operations on books.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

## Getting Started

To get started with the Book Service, follow the instructions below.

### Prerequisites

- Java Development Kit (JDK) 8 or higher installed
- Apache Maven installed

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Build the project using Maven: mvn clean install

### Usage
1. Start the application: mvn spring-boot:run
2. The Book Service will be accessible at http://localhost:9000 (this is port of api gateway, which is part of same microservice)
3. Use a REST client (e.g., Postman) to interact with the available endpoints

### Endpoints
The Book Service provides the following endpoints:
1. GET /books: Retrieves all books.
2. GET /books/{id}: Retrieves a book by ID.
3. POST /books: Creates a new book.
4. PUT /books/{id}: Updates an existing book.
5. DELETE /books/{id}: Deletes a book by ID.



      


