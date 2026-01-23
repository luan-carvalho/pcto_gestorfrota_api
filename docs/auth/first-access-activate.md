## PATCH /auth/first-access/activate

Activates the user and saves the created password.

---

### Request Fields

| Field          | Type   | Required | Description                   |
| -------------- | ------ | -------- | ----------------------------- |
| `registration` | string | Yes      | User registration (matrícula) |
| `token`        | string | Yes      | User's first access token     |
| `password`     | string | Yes      | User's created password       |

> **⚠️ OBS:** _The password must be 8 characters long._

---

### Responses

#### ✅ 200 OK

The users has been activated and it's password was saved. The user can, now, login with its credentials.

#### ❌ 404 Not Found

There's no user with the given registration.
