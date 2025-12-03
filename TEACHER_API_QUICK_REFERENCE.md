# Teacher API Quick Reference Guide

## 🚀 Quick Start

### Base URL
```
http://localhost:8080/api/teachers
```

---

## API Endpoints Quick Reference

### 1️⃣ CREATE - Add New Teacher
```http
POST /api/teachers
Content-Type: application/json

{
  "name": "John Doe",
  "employeeId": "T001",
  "email": "john@school.com",
  "phoneNumber": "9876543210",
  "address": "123 School Lane",
  "qualification": "B.Ed",
  "specialization": "Mathematics",
  "school": {
    "id": 1
  }
}
```
**Response:** `201 CREATED`

---

### 2️⃣ READ ALL - Get All Teachers
```http
GET /api/teachers
```
**Response:** `200 OK` - Array of teachers

---

### 3️⃣ READ BY ID - Get Specific Teacher
```http
GET /api/teachers/1
```
**Response:** `200 OK` - Single teacher object or `404 NOT FOUND`

---

### 4️⃣ READ BY EMPLOYEE ID
```http
GET /api/teachers/employee/T001
```
**Response:** `200 OK` - Single teacher object or `404 NOT FOUND`

---

### 5️⃣ READ BY SCHOOL
```http
GET /api/teachers/school/1
```
**Response:** `200 OK` - Array of teachers in school

---

### 6️⃣ READ BY NAME
```http
GET /api/teachers/name/John%20Doe
```
**Response:** `200 OK` - Array of matching teachers

---

### 7️⃣ UPDATE - Modify Teacher
```http
PUT /api/teachers/1
Content-Type: application/json

{
  "email": "newemail@school.com",
  "phoneNumber": "9999999999",
  "qualification": "M.Ed"
}
```
**Response:** `200 OK` - Updated teacher or `404 NOT FOUND`

---

### 8️⃣ DELETE - Remove Teacher
```http
DELETE /api/teachers/1
```
**Response:** `204 NO CONTENT` or `404 NOT FOUND`

---

### 9️⃣ CHECK EXISTS
```http
GET /api/teachers/exists/1
```
**Response:** `200 OK` - Boolean (true/false)

---

### 🔟 GET COUNT
```http
GET /api/teachers/count
```
**Response:** `200 OK` - Integer (total count)

---

## CURL Examples

### Create Teacher
```bash
curl -X POST http://localhost:8080/api/teachers \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Alice Smith",
    "employeeId":"T101",
    "email":"alice@school.com",
    "phoneNumber":"8765432109",
    "address":"456 Oak Street",
    "qualification":"B.Tech",
    "specialization":"Computer Science",
    "school":{"id":1}
  }'
```

### Get All Teachers
```bash
curl http://localhost:8080/api/teachers
```

### Get Teacher by ID
```bash
curl http://localhost:8080/api/teachers/1
```

### Get Teacher by Employee ID
```bash
curl http://localhost:8080/api/teachers/employee/T101
```

### Get Teachers by School
```bash
curl http://localhost:8080/api/teachers/school/1
```

### Search by Name
```bash
curl "http://localhost:8080/api/teachers/name/Alice%20Smith"
```

### Update Teacher
```bash
curl -X PUT http://localhost:8080/api/teachers/1 \
  -H "Content-Type: application/json" \
  -d '{
    "email":"alice.new@school.com",
    "phoneNumber":"7777777777"
  }'
```

### Delete Teacher
```bash
curl -X DELETE http://localhost:8080/api/teachers/1
```

### Check if Teacher Exists
```bash
curl http://localhost:8080/api/teachers/exists/1
```

### Get Total Count
```bash
curl http://localhost:8080/api/teachers/count
```

---

## Request Body Samples

### Minimal Teacher (Required Fields)
```json
{
  "name": "John Doe",
  "employeeId": "T001",
  "school": {
    "id": 1
  }
}
```

### Complete Teacher
```json
{
  "name": "John Doe",
  "employeeId": "T001",
  "email": "john.doe@school.com",
  "phoneNumber": "9876543210",
  "address": "123 Teacher Street",
  "qualification": "B.Ed",
  "specialization": "Mathematics",
  "school": {
    "id": 1
  }
}
```

### Partial Update (Only Modified Fields)
```json
{
  "email": "newemail@school.com",
  "phoneNumber": "9999999999"
}
```

---

## Response Examples

### Success Response (Create - 201)
```json
{
  "id": 1,
  "name": "John Doe",
  "employeeId": "T001",
  "email": "john@school.com",
  "phoneNumber": "9876543210",
  "address": "123 School Lane",
  "qualification": "B.Ed",
  "specialization": "Mathematics",
  "school": {
    "id": 1,
    "name": "Central School"
  }
}
```

### Success Response (Get All - 200)
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "employeeId": "T001",
    "email": "john@school.com",
    ...
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "employeeId": "T002",
    "email": "jane@school.com",
    ...
  }
]
```

### Error Response (Not Found - 404)
```json
{
  "error": "Teacher not found",
  "status": 404
}
```

---

## HTTP Status Codes

| Code | Meaning | When |
|------|---------|------|
| 201 | Created | Successfully created teacher |
| 200 | OK | Request successful (GET, PUT) |
| 204 | No Content | Successful deletion |
| 400 | Bad Request | Invalid input data |
| 404 | Not Found | Teacher doesn't exist |
| 409 | Conflict | Duplicate employee ID |
| 500 | Server Error | Internal server error |

---

## Field Validation Rules

| Field | Type | Required | Constraints |
|-------|------|----------|-------------|
| id | Long | No (Auto-generated) | - |
| name | String | Yes | - |
| employeeId | String | Yes | Unique, Not Null |
| email | String | No | Email format |
| phoneNumber | String | No | 10-12 digits |
| address | String | No | - |
| qualification | String | No | B.Ed, B.Tech, M.Tech, etc. |
| specialization | String | No | Subject/Domain name |
| school | School | Yes | Valid School ID |

---

## Common Use Cases

### Get All Teachers from a School
```bash
curl http://localhost:8080/api/teachers/school/{schoolId}
```

### Search for Teacher by Name
```bash
curl "http://localhost:8080/api/teachers/name/{teacherName}"
```

### Verify Teacher Exists Before Operations
```bash
curl http://localhost:8080/api/teachers/exists/{teacherId}
```

### Get Total Number of Teachers
```bash
curl http://localhost:8080/api/teachers/count
```

### Complete Update Workflow
```bash
# 1. Check if teacher exists
curl http://localhost:8080/api/teachers/exists/1

# 2. Get current teacher info
curl http://localhost:8080/api/teachers/1

# 3. Update with new information
curl -X PUT http://localhost:8080/api/teachers/1 \
  -H "Content-Type: application/json" \
  -d '{"email":"new@school.com"}'

# 4. Verify update
curl http://localhost:8080/api/teachers/1
```

---

## Important Notes

1. **Employee ID** must be unique - cannot have duplicates
2. **School ID** is required - teacher must belong to a school
3. **Update operations** are partial - only include fields to update
4. **Name search** is case-sensitive - use exact match
5. **Delete is permanent** - cannot be undone
6. **Responses** always include all fields for created/updated records

---

## Testing the API

### Using Postman
1. Import as collection
2. Set base URL to `http://localhost:8080`
3. Create requests with examples above
4. Test each endpoint

### Using CURL
```bash
# Copy examples from this guide
# Replace {id} with actual IDs
# Adjust JSON payloads as needed
```

### Using Browser (GET requests only)
```
http://localhost:8080/api/teachers
http://localhost:8080/api/teachers/1
http://localhost:8080/api/teachers/count
http://localhost:8080/api/teachers/exists/1
```

---

## Troubleshooting

**404 Not Found**
- Check if teacher ID exists
- Use `/api/teachers/count` to verify data exists
- Use `/api/teachers/exists/{id}` to check

**409 Conflict (Duplicate)**
- Employee ID already exists
- Generate new unique employee ID

**400 Bad Request**
- Check JSON format
- Verify required fields present
- Check data types match

**500 Internal Error**
- Check application logs
- Verify database connection
- Ensure MySQL is running

---

## File Reference
- **Model**: `src/main/java/com/schoolapp/attendance/model/Teacher.java`
- **Repository**: `src/main/java/com/schoolapp/attendance/repository/TeacherRepository.java`
- **Service**: `src/main/java/com/schoolapp/attendance/service/TeacherService.java`
- **Controller**: `src/main/java/com/schoolapp/attendance/controller/TeacherController.java`

---

**Last Updated:** December 3, 2025
**Status:** ✅ Active & Tested

