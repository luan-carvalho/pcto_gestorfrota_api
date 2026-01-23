## PATCH /auth/first-access/verify

Verify if a user is in its first-access.

---

### Request Fields

| Field          | Type   | Required | Description                   |
| -------------- | ------ | -------- | ----------------------------- |
| `registration` | string | Yes      | User registration (matrícula) |
| `token`        | string | Yes      | User's first access token     |

---

### Responses

#### ✅ 200 OK

The users exists and the it has not yet been activated.

#### ❌ 404 Not Found

There's no user with the given registration.
