# Endterm Chatbot Platform REST API

REST API for a chatbot platform developed as an endterm project.
The system provides CRUD operations for bots and users, as well as chat session management using REST principles.

*! Database schema is provided in docs/schema.sql*

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring JDBC
- PostgreSQL
- Maven
- Postman

---

## Project Structure

endterm-chatbot-api/
- src/
- pom.xml
- README.md
- docs/
    - screenshots/

---

## Database Design

The system uses a relational PostgreSQL database with the following main entities:
- Bots
- Users
- Chat Sessions

Entity relationships are illustrated in the UML diagram below.

![UML Diagram](docs/uml.png)

---

## How to Run the Project

1. Create a PostgreSQL database.
2. Configure database credentials in application.properties.
3. Run the application using Maven.
4. Server starts at http://localhost:8080

---

## REST API Endpoints

All endpoints were tested using Postman.

---

## Bots API

### GET all bots
GET /api/bots

![GET Bots](docs/screenshots/bots_get.png)

### POST create bot
POST /api/bots

Sample request:
```json
{
  "name": "SupportBot",
  "greeting": "Hi! How can I help?",
  "definition": "You are a helpful assistant for customer support.",
  "tokenLimit": 2048
}
```

Sample response:
```json
{
  "id": 1,
  "name": "SupportBot",
  "greeting": "Hi! How can I help?",
  "definition": "You are a helpful assistant for customer support.",
  "tokenLimit": 2048
}
```

![POST Bot](docs/screenshots/bots_post.png)

### PUT update bot
PUT /api/bots/{id}

Sample request:
```json
{
  "name": "SupportBot v2",
  "greeting": "Hello!",
  "definition": "You are a helpful assistant for customer support.",
  "tokenLimit": 4096
}
```

![PUT Bot](docs/screenshots/bots_put.png)

### DELETE bot
DELETE /api/bots/{id}

![DELETE Bot](docs/screenshots/bots_delete.png)

---

## Users API

### GET all users
GET /api/users

![GET Users](docs/screenshots/users_get.png)

### POST create user
POST /api/users

Sample request:
```json
{
  "name": "Alice",
  "persona": "Student",
  "premium": true
}
```

Sample response:
```json
{
  "id": 1,
  "name": "Alice",
  "persona": "Student",
  "premium": true
}
```

![POST User](docs/screenshots/users_post.png)

### PUT update user
PUT /api/users/{id}

![PUT User](docs/screenshots/users_put.png)

### DELETE user
DELETE /api/users/{id}

![DELETE User](docs/screenshots/users_delete.png)

---

## Chat Sessions API

### POST create chat session
POST /api/sessions

Sample request:
```json
{
  "botId": 1,
  "userId": 1,
  "tokensUsed": 120
}
```

Sample response:
```json
{
  "id": 1,
  "bot": {
    "id": 1,
    "name": "SupportBot",
    "greeting": "Hi! How can I help?",
    "definition": "You are a helpful assistant for customer support.",
    "tokenLimit": 2048
  },
  "user": {
    "id": 1,
    "name": "Alice",
    "persona": "Student",
    "premium": true
  },
  "startedAt": "2026-02-09T12:00:00Z",
  "totalTokensUsed": 120
}
```

![POST Session](docs/screenshots/sessions_post.png)

### GET all chat sessions
GET /api/sessions

![GET Sessions](docs/screenshots/sessions_get.png)

### PATCH update session tokens
PATCH /api/sessions/{id}/tokens

![PATCH Session](docs/screenshots/sessions_patch.png)

---

## REST Design Notes

- POST is used to create resources.
- GET retrieves resources.
- PUT updates entire resources.
- PATCH is used for partial updates.
- 204 No Content indicates successful operation without response body.

---

## Conclusion

This project demonstrates a RESTful backend architecture with proper HTTP semantics,
relational database integration, and full CRUD functionality.

---

## Design Patterns

### Singleton
Implemented as `AppLogger` (`edu.aitu.chatbot.patterns.singleton.AppLogger`).
Used in the Service layer to log key actions (create/update/delete and retrieval).
This class guarantees a single shared instance for the whole application.

### Builder
Implemented as `BotBuilder` (`edu.aitu.chatbot.patterns.builder.BotBuilder`).
Used to construct `Bot` objects with fluent calls and optional fields (`greeting`, `definition`).

### Factory
Implemented as `ChatParticipantFactory` (`edu.aitu.chatbot.patterns.factory.ChatParticipantFactory`).
Creates participants via a common base type (`ChatParticipantBase`) depending on `ParticipantType`.
Used in the Service layer to centralize creation logic for `Bot` and `User`.

---

## Component Principles

### REP (Reuse/Release Equivalence Principle)
Reusable modules are separated into stable packages: `repository`, `service`, `utils`, `patterns`.
These can be released and reused together as independent units.

### CCP (Common Closure Principle)
Classes that change for the same reason are grouped together:
Controllers (`controller`) change with API surface, repositories (`repository`) change with SQL, models (`model`) change with domain rules.

### CRP (Common Reuse Principle)
Dependencies are kept minimal: controllers depend on services only, services depend on repositories/models only, and reusable helpers are in `utils` and `patterns` to avoid pulling unrelated classes.

---

## SOLID & OOP Summary

- SRP: Controllers handle HTTP, Services handle business logic, Repositories handle DB.
- OCP: New participant types can be added by extending factory logic without rewriting controller logic.
- LSP: `Bot` and `User` are both usable as `ChatParticipantBase` where appropriate.
- ISP: Small interfaces (`Loggable`, `Tokenizable`, `Validatable`) avoid forcing unused methods.
- DIP: Higher-level layers depend on abstractions (service uses repositories via interfaces where applicable and central creation via factory).

---

## Database Schema

SQL schema is available in `docs/schema.sql`.

---

## System Architecture Diagram

Layered architecture: Controller → Service → Repository → Database.
UML Diagram:

![UML Diagram](docs/uml.png)
