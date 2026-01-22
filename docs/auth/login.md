## POST /auth/login

Authenticates an existing user using their **registration (matrícula)** and **password**.

---

### Request Fields

| Field          | Type   | Required | Description                   |
| -------------- | ------ | -------- | ----------------------------- |
| `registration` | string | Yes      | User registration (matrícula) |
| `password`     | string | Yes      | User password                 |

---

### Responses

#### ✅ 200 OK - Authenticated successfully

Authentication successful. Returns a JWT token and user details.

| Field             | Type   | Description               |
| ----------------- | ------ | ------------------------- |
| `token`           | string | JWT access token          |
| `type`            | string | Token type (`Bearer`)     |
| `username`        | string | Authenticated user's name |
| `roleDescription` | string | User role description     |

#### ❌ 401 Unauthorized - Invalid credentials.

Invalid registration or password.