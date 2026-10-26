# Full Stack Blog Platform

A full-stack blogging platform built with **Java, Spring Boot, Spring Security, PostgreSQL, and Next.js**. The application provides REST APIs for user authentication, blog posts, comments, likes, friend requests, and paginated data retrieval.

The project follows a layered backend architecture to keep business logic, API handling, and database operations organized and maintainable.

## 🚀 Features

### 🔐 Authentication and Security
- User authentication using JWT.
- Authentication and authorization with Spring Security.
- Protected API endpoints.
- Role-based access control where configured.

### 📝 Blog Management
- Create and manage blog posts.
- Retrieve blog content through REST APIs.
- Support for comments and likes.
- Friend request functionality.
- Paginated data retrieval.

### ⚙️ Backend
- RESTful API development using Spring Boot.
- Layered architecture: Controller, Service, Repository, DTO, and Entity.
- Database persistence using Spring Data JPA and PostgreSQL.
- Centralized exception handling and request validation, where implemented.

### 💻 Frontend
- Built with Next.js and React.
- State management using Redux Toolkit.
- Communicates with backend REST APIs.

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| Language | Java |
| Backend Framework | Spring Boot |
| API Development | Spring MVC, REST APIs |
| Security | Spring Security, JWT |
| ORM | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Frontend | Next.js, React.js |
| State Management | Redux Toolkit |
| API Testing | Postman |
| Build Tool | Maven |
| Version Control | Git, GitHub |

## 🏗️ Architecture

The backend uses a layered architecture to separate responsibilities.

```text
Client (Next.js)
       |
       | HTTP Requests / REST APIs
       v
Spring Security
       |
       v
Controller Layer
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
PostgreSQL Database
```

### Layer Responsibilities

- **Controller:** Receives HTTP requests and returns API responses.
- **Service:** Contains business logic and coordinates operations.
- **Repository:** Handles database access through Spring Data JPA.
- **Entity:** Represents database-persisted objects.
- **DTO:** Defines data transferred between the client and backend.
- **Spring Security:** Protects endpoints and handles authentication and authorization.

## 📂 Project Structure

The backend follows a structure similar to the following:

```text
src/
└── main/
    ├── java/
    │   └── com/
    │       └── yourpackage/
    │           ├── controller/
    │           ├── service/
    │           ├── repository/
    │           ├── entity/
    │           ├── dto/
    │           ├── config/
    │           ├── security/
    │           ├── exception/
    │           └── BlogApplication.java
    │
    └── resources/
        └── application.properties
```

*Note: The package names and folders above are illustrative. Adjust them to match your actual repository structure.*

## ⚙️ Getting Started

Follow these steps to run the backend locally.

### Prerequisites

Install the following:

- Java JDK compatible with your Spring Boot version.
- Maven, or use the Maven Wrapper if included.
- PostgreSQL.
- Git.
- An IDE such as IntelliJ IDEA or VS Code.

### 1. Clone the Repository

```bash
git clone https://github.com/buddheshwar2003/simple_blog_website_backend.git
```

Navigate to the project directory:

```bash
cd simple_blog_website_backend
```

### 2. Create the Database

Open PostgreSQL and create a database for the application:

```sql
CREATE DATABASE blog_db;
```

Use the database name and credentials that match your application configuration.

### 3. Configure the Application

Open `src/main/resources/application.properties`.

Configure your PostgreSQL connection using environment variables or your local development configuration.

For example:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Set the corresponding environment variables:

```text
DB_URL=jdbc:postgresql://localhost:5432/blog_db
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
```

Configure JWT-related properties according to your application's security implementation.

**Security note:** Never commit real database passwords, JWT secrets, private keys, or other credentials to GitHub. Use environment variables or a secrets manager.

### 4. Run the Backend

Using the Maven Wrapper on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

The backend will start on the port configured by your application, commonly `8080`.

### 5. Test the APIs

Use Postman or another HTTP client to test the endpoints.

1. Register or create a test user using your implemented registration flow.
2. Authenticate using the login endpoint.
3. Obtain the JWT token if your login flow returns one.
4. Include the token when calling protected endpoints.
5. Test blog posts, comments, likes, and other implemented features.

For protected endpoints, the request header may look like this if your security configuration uses Bearer tokens:

```http
Authorization: Bearer <your-jwt-token>
```

Use the actual endpoint paths and request formats defined by your backend.

## 🔌 API Overview

The following table describes the main API categories. Exact paths, HTTP methods, and request formats depend on the implementation.

| Module | Purpose |
|---|---|
| Authentication | User registration and login |
| Users | User-related operations |
| Posts | Blog post management |
| Comments | Add and retrieve comments |
| Likes | Like-related operations |
| Friend Requests | Manage friend requests |

For detailed API documentation, add your actual endpoint paths and example requests here. If Swagger/OpenAPI is configured, link to its UI once you confirm the correct URL.

## 🧪 Testing

Use Postman to verify:

- Successful and unsuccessful authentication.
- Access to protected endpoints.
- Blog post creation and retrieval.
- Comment and like operations.
- Validation errors and exception responses.
- Pagination behavior.
- Database persistence.

Automated tests can also be added using JUnit and Mockito.

## 🔮 Future Improvements

Potential improvements include:

- Automated unit and integration tests.
- Swagger/OpenAPI documentation.
- Docker Compose for local application and database setup.
- CI/CD automation.
- Improved logging and monitoring.
- Rate limiting and additional security controls.
- Deployment and production configuration improvements.

These are proposed improvements, not claims that every feature is already implemented.

## 👨‍💻 Author

**Buddheshwar Bhakat**

- GitHub: [@buddheshwar2003](https://github.com/buddheshwar2003)
- LinkedIn: [Buddheshwar Bhakat](https://linkedin.com/in/buddheshwar2003)

## 📄 License

Add a license file if you intend to distribute the project publicly. Until a license is selected, don't assume that others have permission to reuse, modify, or redistribute the code.
