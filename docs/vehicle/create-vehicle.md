## POST /admin/vehicle

Create a new vehicle.

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
| `make`         | string | Yes      | Vehicle make (marca)            |
| `model`        | string | Yes      | Vehicle's model                 |
| `licensePlate` | string | Yes      | Vehicle's license plate (placa) |
| `type`         | string | No       | Vehicle type                    |
| `capacity`     | number | No       | Vehicle capacity                |
| `mileage`      | number | No       | Vehicle mileage (quilometragem) |

##### _Allowed values for type_

- PICKUP
- SUV
- SEDAN
- HATCH
- MOTORCYCLE

---

### Responses

#### ✅ 201 Created - Vehicle created

| Field          | Type   | Description                       |
| -------------- | ------ | --------------------------------- |
| `id`           | number | Vehicle's internal id             |
| `make`         | string | Vehicle's make (marca)            |
| `model`        | string | Vehicle's model                   |
| `licensePlate` | string | Vehicle's license plate (placa)   |
| `type`         | string | Vehicle's type                    |
| `capacity`     | number | Vehicle's capacity                |
| `mileage`      | number | Vehicle's mileage (quilometragem) |