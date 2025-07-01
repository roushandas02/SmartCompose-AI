# SmartCompose AI

SmartCompose AI is an intelligent email assistant that helps you draft professional, context-aware replies with minimal effort. It combines a Chrome extension, a React frontend, and a Spring Boot backend integrated with Gemini AI to deliver fast, customized email responses.

---

## 1. Chrome Extension

The Chrome extension integrates directly with Gmail. It injects a **“Generate Reply”** button into the compose box, enabling you to instantly create AI-powered replies based on the current email thread. It also includes a popup panel to configure tone, style, and default templates.

![Screenshot (1075)](https://github.com/user-attachments/assets/40b03986-b737-46a4-8312-1a1b276b1ad5)

![Screenshot (1074)](https://github.com/user-attachments/assets/b2f1a9cb-cca1-4198-87fc-f73f740fc5ac)



---

## 2. React Frontend

The React frontend application allows users to:
- Paste the original email text.
- Select the desired tone (formal, friendly, concise, etc.).
- Provide an optional custom prompt.
- Generate a polished reply with one click.

It also includes login and signup pages connected to the backend for secure access.

![Screenshot (1073)](https://github.com/user-attachments/assets/af3080e7-fec8-4908-9088-757085b53b24)

![Screenshot (1072)](https://github.com/user-attachments/assets/cf27560f-d80f-42b9-b9b8-1ed4ba2acde1)


---

## 3. Spring Boot Backend

The backend is built with Spring Boot and provides REST APIs for:
- Generating email replies by sending input to the Gemini API and returning the response.
- Handling user authentication with JWT-based signin and signup endpoints.


### 🔑 Authentication Flow

**Register User**

POST /register
- Stores hashed password in MySQL.

**Login**

POST /login
- Validates credentials.

Returns JWT access token.

Access Protected APIs
Attach **Authorization:** Bearer <token> header.

JWT is validated in a custom filter.

**Role-Based Access**

Endpoints can be secured by roles (hasAuthority("ADMIN")).


It is designed for secure, high-performance integration with both the extension and frontend.

---

## 4. mySQL Database

This project has a robust Spring Boot backend application that implements secure authentication and authorization workflows. It leverages **MySQL** as the primary relational database and uses **Hibernate** as the ORM (Object-Relational Mapping) framework to manage entity persistence and database interactions seamlessly.The default ORM (Object-Relational Mapping) used by Spring Data JPA is Hibernate.  User credentials, roles, and related metadata are stored securely with support for **password hashing** and **token-based authentication.** The backend exposes RESTful APIs for user registration, login, and access control. With a clean, modular architecture, this project is ideal for building scalable services that require reliable user management, secure data persistence, and efficient ORM capabilities.



![Screenshot (1121)](https://github.com/user-attachments/assets/34b10fab-5257-4bc0-b851-c9c61558d106)

You don’t need to extend or implement any specific Hibernate classes or interfaces to use it. Simply use standard JPA annotations like @Entity, @Id, and @Column in your entity classes. Hibernate works as the JPA provider behind the scenes, handling persistence automatically.




---

## Getting Started

1. Clone the repositories for the Chrome extension, React frontend, and Spring Boot backend.
2. Start the Spring Boot backend (`mvn spring-boot:run`).
3. Run the React frontend (`npm start`).
4. Load the Chrome extension in developer mode.
5. Create an account and start generating replies.

---

## License

N/A
