# 🏦 FinSec Vault - Secure Transaction Management System

FinSec Vault is a scalable, secure backend infrastructure built using Spring Boot, designed for finance and banking institutions to enable secure user registration, login, OTP verification, transaction handling, and role-based access control.

---

## 🚀 Technologies and Methods Used

- **Spring Boot 3.x**
- **Spring Security (Authentication + Authorization)**
- **JWT (JSON Web Tokens) for Stateless Authentication**
- **Role-Based Access Control (RBAC)**
- **MySQL Database**
- **JPA / Hibernate ORM**
- **OTP Generation and Validation**
- **Exception Handling with ControllerAdvices**
- **DBMS Security Concepts (Normalization, Encryption, Referential Integrity)**

---

## 🧩 Modules

1. **Authentication Module**
   - Register User (with OTP Verification)
   - Login User (JWT Token Generation)

2. **Authorization Module**
   - Role Based Access Control (Customer, Manager, Teller)

3. **Transaction Module**
   - Initiate Transaction
   - Risk Assessment (Flag suspicious transactions)
   - Approval Workflow (Teller/Manager Approval)

4. **OTP Management Module**
   - OTP Generation
   - OTP Verification

5. **Audit Management**
   - Audit key transactions (DB insertions, approvals)

---

## 📋 How to Use & Check Working APIs

### Prerequisites
- Java 17+ installed
- Maven installed
- MySQL installed and configured

### Setup Steps

1. **Clone the repository**

```bash
git clone https://github.com/adhishpawar/FinSec-Vault-Secure-Backend-Infrastructure-for-Financial-Applications.git
cd finsec-vault
```

2. **Setup the Database**

- Create a database named `finsec_vault`

```sql
CREATE DATABASE finsec_vault;
```

- Update `application.properties` with your database credentials

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/finsec_vault
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. **Build and Run the Project**

```bash
mvn clean install
mvn spring-boot:run
```

4. **Access Swagger UI for Testing APIs**

Once application is running:

- Navigate to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### API Endpoints Summary

| Action                  | Endpoint                  | Method | Auth Required |
|--------------------------|----------------------------|--------|---------------|
| Register User            | `/api/auth/register`       | POST   | No            |
| Verify OTP               | `/api/auth/verify-otp`      | POST   | No            |
| Login                    | `/api/auth/login`          | POST   | No            |
| Initiate Transaction     | `/api/transactions`        | POST   | Yes           |
| Approve Transaction      | `/api/transactions/approve/{txnId}` | PATCH | Yes (Role: Teller/Manager) |

### Example User Roles

- **Customer** — Can initiate transactions
- **Teller** — Can approve normal transactions
- **Manager** — Can approve flagged (risky) transactions

### Default Roles You Can Create

```sql
INSERT INTO roles(role_name) VALUES ('ROLE_CUSTOMER'), ('ROLE_TELLER'), ('ROLE_MANAGER');
```

---

## 📂 Project Folder Structure

```
src/main/java/com/finsecvault
│
├── config          # Spring Security, JWT Configurations
├── controller      # API Layer
├── dto             # Request and Response Models
├── entity          # JPA Entity Models
├── repository      # Data Access Layer
├── service         # Business Logic Layer
├── exception       # Custom Exception Handlers
└── util            # Utility Classes (e.g., OTP Generator)
```

---


> Developed with ❤️ for secure financial systems.
