## PATCH /admin/user/`{userId}`

Update user info.

---

### 🔐 Security

| Requirement      | Value                |
| ---------------- | -------------------- |
| `Authentication` | **Required**         |
| `Token type`     | Bearer (JWT)         |
| `Roles`          | Admin, Fleet Manager |

---

### Request fields

| Field          | Type   | Required | Description                   |
| -------------- | ------ | -------- | ----------------------------- |
| `registration` | string | No       | User registration (matrícula) |
| `name`         | string | No       | User's first access token     |
| `roleId`       | number | No       | User's role id                |

##### _Allowed values for roleId_

| ID  | Role name     | Description                         |
| --- | ------------- | ----------------------------------- |
| `1` | Admin         | Full system access                  |
| `2` | Fleet Manager | Fleet management permissions        |
| `3` | Driver        | Can see its trips                   |
| `4` | Agent         | Can request vehicles for operations |

---

### Responses

#### ✅ 200 OK - User details

| Field          | Type   | Description                     |
| -------------- | ------ | ------------------------------- |
| `id`           | number | User's internal id              |
| `registration` | string | User's registration (matrícula) |
| `status`       | string | User's first-access status      |
| `role`         | string | User's role                     |
