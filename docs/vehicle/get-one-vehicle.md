## GET /admin/vehicle/`{vehicleId}`

Return a vehicle details for the given vehicle id.

---

### 🔐 Security

| Requirement    | Value                |
| -------------- | -------------------- |
| Authentication | **Required**         |
| Token type     | Bearer (JWT)         |
| Roles          | Admin, Fleet Manager |

---

### Responses

#### ✅ 200 OK - Vehicle details

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
