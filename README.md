![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)
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
🔑 Authentication APIs (/auth)

Method	Endpoint	Description
POST	/auth/register	Register a new user (password encrypted using BCrypt)
POST	/auth/login	Authenticate user and generate access & refresh tokens
POST	/auth/refresh	Generate a new access token using refresh token
GET	/auth/users	Fetch authenticated user details (secured endpoint)

📦 Product APIs
Method	Endpoint	Description
GET	/products	Fetch all products
GET	/product/{id}	Fetch product by ID
POST	/product	Add a new product with image upload
PUT	/product/{id}	Update product details and image
DELETE	/product/{id}	Delete product
GET	/products/search?keyword=	Search products by keyword
GET	/product/{productId}/image	Fetch product image by product ID

📌 Features used: Multipart file upload, binary image storage, keyword search

🧾 Order APIs
Method	Endpoint	Description
POST	/orders/place	Place an order
GET	/orders	Fetch all orders

🏗 Architecture
Controller → Service → Repository → Database

DTOs used for clean request/response handling
Separation of concerns followed

📊 API Coverage

✅ 15+ REST APIs
✅ Authentication + Refresh Token Flow
✅ Product CRUD with Image Handling
✅ Order Management
