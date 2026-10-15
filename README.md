# Employee Management System — PeopleDesk

A Java web application for managing employee records. It demonstrates CRUD operations, server-side validation, role-based access, Spring MVC, Spring Data JPA, Thymeleaf, and an embedded H2 database.

## Features

- Add, view, search, edit, and delete employee records.
- Employee fields: name, email, department, designation, salary, and joining date.
- Role-based access: administrators can manage records; viewers are read-only.
- Form validation and duplicate-email handling.
- Dashboard cards for employee and department counts.
- Database persists locally; no separate MySQL installation is required.
- Four sample employees are inserted automatically the first time the app runs.

## Technology stack

Java 17+, Spring Boot 3.5.16, Spring MVC, Spring Security, Spring Data JPA/Hibernate, Thymeleaf, Bean Validation, and H2.

## Prerequisites

1. JDK 17 or newer.
2. Apache Maven 3.6.3 or newer.
3. A web browser.

Check installations in a terminal:

```bash
java -version
mvn -version
```

## Run the application

Open a terminal in this folder and run:

```bash
mvn spring-boot:run
```

On Windows, you can also double-click `run-windows.bat` from this project folder.

Open **http://localhost:8081**.

### Demo accounts

| Username | Password | Access |
| --- | --- | --- |
| `admin` | `admin123` | Add, view, edit, and delete employees |
| `viewer` | `viewer123` | View and search only |

These demo credentials are for local evaluation. Replace the in-memory accounts and configure production-grade identity management before deploying this application publicly.

## Database

H2 stores its file database at `./data/employee-management`. The database is created automatically. To reset the sample database, stop the application and remove the `data` folder; the sample records will be inserted on the next start.

## Project structure

```text
src/main/java/com/example/employeemanagement/
  config/       SecurityConfig.java, DataInitializer.java
  controller/   EmployeeController.java, WebController.java
  model/        Employee.java
  repository/   EmployeeRepository.java
  EmployeeManagementApplication.java
src/main/resources/
  static/css/style.css
  templates/login.html
  templates/employees/list.html
  templates/employees/form.html
  application.properties
```

## GitHub publishing

Create an empty GitHub repository named `employee-management-system` (do not initialise it with a README). From this project folder, run:

```bash
git init
git add .
git commit -m "Initial commit: Employee Management System"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/employee-management-system.git
git push -u origin main
```

Replace `YOUR_USERNAME` with your GitHub username. Do not commit passwords, API keys, or the local `data/` database folder.
