## GET /admin/vehicle

Return all vehicles.

---

### 🔐 Security

| Requirement    | Value                |
| -------------- | -------------------- |
| Authentication | **Required**         |
| Token type     | Bearer (JWT)         |
| Roles          | Admin, Fleet Manager |

---

### Responses

#### ✅ 200 OK - List of all vehicles

**Response type:** Array of `Vehicle`

> Each item in the response array represents a vehicle.

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
