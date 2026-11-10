# Banking Management System

A full-stack Banking Management System built to manage banking operations through a web-based interface, with secure authentication and persistent database storage.

<p align="center">
  <a href="https://bms-coral.vercel.app/"><strong>🚀 Live Demo</strong></a>
</p>

## Overview

The Banking Management System is a full-stack web application designed to simplify essential banking operations. It uses a Spring Boot backend to handle business logic and REST APIs, JWT-based authentication to secure access, and PostgreSQL to store application data.

## Key Features

- **Secure Authentication:** Login system with JWT-based authentication.
- **Account Management:** Create and manage bank accounts.
- **Deposit Operations:** Add money to an account.
- **Withdrawal Operations:** Withdraw money from an account.
- **Transaction Management:** Maintain transaction records.
- **Database Persistence:** Store application data using PostgreSQL.
- **RESTful APIs:** Connect the frontend with backend services.

## Tech Stack

| Layer | Technologies |
|---|---|
| Backend | Java 17, Spring Boot 3.3.5 |
| Security | Spring Security, JWT |
| Database | PostgreSQL, Spring Data JPA |
| Frontend | HTML, CSS, JavaScript |
| Deployment | Render, Vercel |
| Build Tool | Maven |
| Version Control | Git, GitHub |

## Architecture

```text
Frontend (Vercel)
       |
       | HTTP Requests / REST APIs
       v
Spring Boot Backend (Render)
       |
       | Spring Data JPA
       v
PostgreSQL Database (Neon)
```

## Project Structure

```text
Banking-Management-System/
│
├── banking-backend/
│   ├── frontend/
│   │   └── index.html
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   ├── Dockerfile
│   └── pom.xml
│
└── README.md
```

## Live Deployment

- **Frontend:** https://bms-coral.vercel.app/
- **Backend:** https://banking-management-system-api.onrender.com

## Getting Started

### Prerequisites

- Java 17
- Maven
- PostgreSQL
- Git

### Run Locally

1. Clone the repository:

```bash
git clone https://github.com/ramamangtani26/Banking-Management-System.git
```

2. Navigate to the backend project:

```bash
cd Banking-Management-System/banking-backend
```

3. Configure your database and application environment variables.

4. Run the application:

```bash
mvn spring-boot:run
```

5. Open the frontend `index.html` file in your browser.

## Future Enhancements

- Improved dashboard with account summaries.
- Advanced transaction filtering and search.
- Enhanced input validation and error handling.
- Role-based access control.
- Automated testing and improved security.

## Author

**Rama Mangtani**

- GitHub: [@ramamangtani26](https://github.com/ramamangtani26)
- LinkedIn: [Rama Mangtani](https://www.linkedin.com/in/rama-mangtani/)

---

*Developed as a full-stack software engineering project to explore backend development, authentication, database integration, and application deployment.*
