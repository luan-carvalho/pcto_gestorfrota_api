## POST /admin/vehicle

Update an existing vehicle info.

---

### 🔐 Security

| Requirement      | Value                |
| ---------------- | -------------------- |
| `Authentication` | **Required**         |
| `Token type`     | Bearer (JWT)         |
| `Roles`          | Admin, Fleet Manager |

---

### Request fields

| Field          | Type   | Required | Description                     |
| -------------- | ------ | -------- | ------------------------------- |
| `make`         | string | No       | Vehicle make (marca)            |
| `model`        | string | No       | Vehicle's model                 |
| `licensePlate` | string | No       | Vehicle's license plate (placa) |
| `type`         | string | No       | Vehicle type                    |
| `capacity`     | number | No       | Vehicle capacity                |

##### _Allowed values for type_

- PICKUP
- SUV
- SEDAN
- HATCH
- MOTORCYCLE

---

### Responses

#### ✅ 200 OK - Vehicle updated

| Field          | Type   | Description                       |
| -------------- | ------ | --------------------------------- |
| `id`           | number | Vehicle's internal id             |
| `make`         | string | Vehicle's make (marca)            |
| `model`        | string | Vehicle's model                   |
| `licensePlate` | string | Vehicle's license plate (placa)   |
| `type`         | string | Vehicle's type                    |
| `status`       | string | Vehicle's status                  |
| `capacity`     | number | Vehicle's capacity                |
| `mileage`      | number | Vehicle's mileage (quilometragem) |
