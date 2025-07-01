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

It is designed for secure, high-performance integration with both the extension and frontend.

---

## 4. H2 In-Memory Relational Database

The backend uses **H2**, a lightweight in-memory relational database, to store user details such as credentials and authentication tokens. H2 is ideal for development and testing, offering fast access with minimal configuration. Data is managed via Spring Data JPA repositories and can be persisted to disk if needed.

![Screenshot (1077)](https://github.com/user-attachments/assets/6e94f33e-4213-4770-9288-adc049479e86)

![Screenshot (1076)](https://github.com/user-attachments/assets/88a4c73e-e8d1-4cb0-b11d-e30e74b205fd)



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
