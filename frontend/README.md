# Travel Wishlist App

A simplified Travel Wishlist application built as a final project for the Full Stack Developer training. It demonstrates a React frontend that communicates with a Spring Boot backend (expected) and a PostgreSQL database. This repository contains the frontend portion located at the project root.

**Project Scope**

- **Admin:** create and manage destinations and tags.
- **User:** browse destinations, add destinations to a personal wishlist, register/login, and manage wishlist items.
- **Optional:** AI-powered suggestion endpoint (e.g., "Suggest me: Beach, Cheap").

**Features Implemented**

- Browse destinations with tags
- User registration and login (frontend flows)
- Add / remove destinations to `My Wishlist`
- Admin pages for creating destinations and managing tags (frontend)

**Tech Stack**

- Frontend: React (Vite)
- Backend (recommended): Spring Boot (REST API)
- Database: PostgreSQL
- Auth: JWT-based authentication (frontend expects JWT tokens)

## Travel Wishlist — Full Stack Final Project

This repository contains the frontend for the Travel Wishlist final project (React + Vite). The full project is a simplified travel wishlist manager with a Spring Boot backend and PostgreSQL database.

### Project Overview

- Admin: create and manage destinations and tags.
- User: browse destinations, register/login, and add/remove destinations to `My Wishlist`.
- Features: authentication, role-based access control, CRUD for destinations and tags, wishlist management.

### Tech Stack

- Backend: Spring Boot (Java)
- Database: PostgreSQL
- Frontend: React (Vite)
- Auth: token-based (JWT), passwords hashed (BCrypt)

### Quickstart — Frontend

Prerequisites: Node.js >= 16, npm (or Yarn). Backend must be running and reachable.

1. Install dependencies

```bash
cd frontend
npm install
```

2. Create environment file (Vite)

Create a `.env` file at `frontend/.env` with:

```text
VITE_API_BASE_URL=http://localhost:8080
```

3. Run development server

```bash
npm run dev
```

4. Build for production

```bash
npm run build
```

Notes: The frontend HTTP clients live in `src/services`. They read `VITE_API_BASE_URL`. Update `src/services/api.js` if your backend uses a different base URL or path.

### Quickstart — Backend (summary)

Prerequisites: Java 17+, Maven, PostgreSQL.

1. Configure the database in `backend/src/main/resources/application.properties` (or via environment variables):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/travel_wishlist
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
```

2. Build and run

```bash
cd backend
mvn clean package
java -jar target/*.jar
# or during development:
mvn spring-boot:run
```

3. Swagger/OpenAPI

When the backend runs locally (default port 8080) the Swagger UI is usually available at:

```
http://localhost:8080/swagger-ui.html
```

Depending on Springdoc version this may be at `/swagger-ui/index.html`.

### Database Schema (high level)

Entities and relationships implemented to meet project requirements:

- `User` (id, username, email, password_hashed, roles, status)
- `Country` (id, name, code)
- `Destination` (id, name, description, image_url, country_id)
  - Relationship: Many `Destination` -> One `Country` (Many-to-One)
- `Tag` (id, name)
- `DestinationTag` (destination_id, tag_id)
  - Relationship: `Destination` <-> `Tag` is Many-to-Many via a join table
- `Wishlist` (id, user_id, destination_id, created_at)
  - Relationship: `User` -> `Wishlist` is One-to-Many (a user can have many wishlist entries)

This satisfies: one One-to-Many (User → Wishlist) and one Many-to-Many (Destination ↔ Tag).

### API Endpoints (summary)

The backend exposes REST controllers under paths similar to:

- `POST /api/auth/register`, `POST /api/auth/login`
- `GET /api/countries`, `POST /api/countries` (admin)
- `GET /api/destinations`, `POST /api/destinations` (admin), `GET /api/destinations/:id`
- `GET /api/tags`, `POST /api/tags` (admin)
- `GET /api/wishlist`, `POST /api/wishlist` (add), `DELETE /api/wishlist/:id`

Protected endpoints require an `Authorization: Bearer <token>` header.

For full API documentation use the Swagger/OpenAPI UI (see above).

### Authentication & Security

- Passwords stored hashed (BCrypt).
- Role-based access control (e.g., `ROLE_USER`, `ROLE_ADMIN`).
- Token-based authentication (JWT). Frontend stores token (e.g., `localStorage`) and sends it with protected requests.

### Testing

- Backend: run `mvn test` in the `backend` folder.
- Frontend: add test scripts as needed; this template does not include tests by default.

### Git & Submission Guidelines

- Use Git with feature branches; commit frequently (daily).
- Keep merged feature branches (do not delete them) for traceability.
- Add instructors with Reporter role / provide read access if repo is private:
  - André Silva (souan)
  - Nelson Ferreira (nels-ferr)
- Submission: share repository link via email or Teams before the deadline (22/01/2026).

### Evaluation Criteria (mapping)

- Functionality (40%): required features working, error handling implemented.
- Code Quality (25%): clean structure, conventions, comments where helpful.
- Database Design (15%): proper relationships, CRUD implemented.
- UI/UX (10%): responsive and usable interface.
- Documentation & Presentation (10%): clear README, Swagger docs, git workflow.

### Deployment (optional)

- Backend: deploy JAR to a VM or container; configure DB connection to a managed Postgres instance.
- Frontend: build (`npm run build`) and serve static files (Netlify, Vercel, or any static hosting). Set `VITE_API_BASE_URL` to the deployed backend.

### Notes & Known TODOs

- Ensure teacher accounts have repository access before submission.
- Consider adding an AI suggestion endpoint (optional) to demonstrate external API/AI integration.

### Contact

For questions about the assignment, contact:

- andre.silva@education.lu
- nelson.ferreira@education.lu

---

File: [frontend/README.md](frontend/README.md)
