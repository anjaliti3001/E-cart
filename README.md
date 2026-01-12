![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue)
![MySQL](https://img.shields.io/badge/Database-MySQL-blue)
![Maven](https://img.shields.io/badge/Build-Maven-red)

## ❓ Why This Project?
This project was built to understand how real-world e-commerce backend systems handle
authentication, secure APIs, file uploads, and transactional order processing using
Spring Boot and JWT-based security.

## 🔐 Security Implementation
- Passwords encrypted using BCrypt
- Authentication handled via Spring Security AuthenticationManager
- Stateless JWT-based authorization
- Access token + Refresh token mechanism
- Refresh tokens stored and validated from database

## API Flow Diagram
Client → Login → JWT Access Token
Client → API Request → Security Filter → Controller
Client → Refresh Token → New Access Token
