## GET /admin/user/`{userId}`

Return a user details for the given user id.

---
### 🔐 Security

| Requirement | Value |
|------------|------|
| Authentication | **Required** |
| Token type | Bearer (JWT) |
| Roles | Admin, Fleet Manager |

---

### Responses

#### ✅ 200 OK - User details


| Field             | Type   | Description               |
| ----------------- | ------ | ------------------------- |
| `id`           | number | User's internal id          |
| `registration`            | string | User's registration (matrícula)     |
| `name`        | string | User's name |
| `role` | string | User's role     |


