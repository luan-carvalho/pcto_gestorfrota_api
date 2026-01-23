## GET /admin/user

Return all users.

---

### 🔐 Security

| Requirement    | Value                |
| -------------- | -------------------- |
| Authentication | **Required**         |
| Token type     | Bearer (JWT)         |
| Roles          | Admin, Fleet Manager |

---

### Responses

#### ✅ 200 OK - List of all users

**Response type:** Array of `User`

> Each item in the response array represents a user.

| Field          | Type   | Description                     |
| -------------- | ------ | ------------------------------- |
| `id`           | number | User's internal id              |
| `registration` | string | User's registration (matrícula) |
| `name`         | string | User's name                     |
| `role`         | string | User's role                     |
