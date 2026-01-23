## GET /actuator/health

Return the API status

---

### Responses

#### ✅ 200 OK

The API is up and running

| Field    | Type   | Description       |
| -------- | ------ | ----------------- |
| `groups` | array  | API health groups |
| `status` | string | API status        |
