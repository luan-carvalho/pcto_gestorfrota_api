## PATCH /auth/update-password

Update the user password

---

### 🔐 Security

| Requirement      | Value                |
| ---------------- | -------------------- |
| `Authentication` | **Required**         |
| `Token type`     | Bearer (JWT)         |
| `Roles`          | Admin, Fleet Manager |

---

### Request fields

| Field             | Type   | Required | Description             |
| ----------------- | ------ | -------- | ----------------------- |
| `currentPassword` | string | Yes      | User's current password |
| `newPassword`     | string | Yes      | User's new password     |

> **⚠️ OBS:** _The password must be 8 characters long._

---

### Responses

#### ✅ 204 No Content - Password Updated

The password was successfully updated