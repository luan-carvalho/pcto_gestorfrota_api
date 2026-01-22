## PUT /admin/user/`{userId}`

Update

---
### 🔐 Security

| Requirement | Value |
|------------|------|
| `Authentication` | **Required** |
| `Token type` | Bearer (JWT) |
| `Roles` | Admin, Fleet Manager |

---

### Request fields

| Field          | Type   | Required | Description                   |
| -------------- | ------ | -------- | ----------------------------- |
| `registration` | string | Yes      | User registration (matrícula) |
| `name`        | string | Yes      | User's first access token     |
| `roleId`        | number | Yes      | User's role id     |

##### *Allowed values for roleId*

| ID  | Role name     | Description                  |
| --- | ------------- | ---------------------------- |
| `1` | Admin | Full system access           |
| `2` | Fleet Manager       | Fleet management permissions |
| `3` | Driver      | Can see its trips   |
| `4` | Agent      | Can request vehicles for operations   |


---


### Responses

#### ✅ 200 OK - User details


| Field             | Type   | Description               |
| ----------------- | ------ | ------------------------- |
| `id`           | number | User's internal id          |
| `registration`            | string | User's registration (matrícula)     |
| `status`            | string | User's first-access status    |
| `role`        | string | User's role |
| `firstAccessToken` | string | User's first access token     |

> **⚠️ OBS:** *The first access token will be used to validate the user first-access status to allow them to create its password to login.*


