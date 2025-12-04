# Circular Reference Serialization Fix

## Issue Fixed

The build was failing with `StackOverflowError` during JSON serialization of entities with bidirectional relationships (circular references).

**Error:**
```
com.fasterxml.jackson.databind.JsonMappingException
    Caused by: java.lang.StackOverflowError
```

---

## Root Cause

Bidirectional relationships between entities caused infinite loops during JSON serialization:
- Teacher ↔ School (many-to-one, one-to-many)
- Teacher ↔ SchoolClass (one-to-many)
- Teacher ↔ ClassSection (one-to-many)
- School ↔ SchoolClass (one-to-many)
- SchoolClass ↔ ClassSection (one-to-many)
- ClassSection ↔ Student (one-to-many)

When Jackson tried to serialize a Teacher, it would serialize the School, which would try to serialize all Teachers again, creating an infinite loop.

---

## Solution Applied

Added `@JsonIgnore` annotations to the "child" side of all bidirectional relationships to break the circular reference during serialization.

### Changes Made

#### 1. Teacher.java
```java
@OneToMany(mappedBy = "classTeacher", cascade = CascadeType.ALL)
@JsonIgnore
private List<SchoolClass> classesTeaching;

@OneToMany(mappedBy = "sectionTeacher", cascade = CascadeType.ALL)
@JsonIgnore
private List<ClassSection> sectionsTeaching;
```

#### 2. School.java
```java
@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<SchoolClass> classes;

@OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<Teacher> teachers;
```

#### 3. SchoolClass.java
```java
@OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<ClassSection> sections;
```

#### 4. ClassSection.java
```java
@OneToMany(mappedBy = "classSection", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<Student> students;
```

---

## How @JsonIgnore Works

`@JsonIgnore` tells Jackson's JSON serializer to skip that field when converting the object to JSON. This prevents:
- ✅ Infinite loops in circular references
- ✅ Stack overflow errors
- ✅ Huge JSON responses with duplicate data

The parent-side references (many-to-one) are still serialized, so you can still see which parent an entity belongs to.

---

## Build Status After Fix

```
BUILD SUCCESSFUL in 4s
9 actionable tasks: 9 executed

All 155 tests PASSED ✅
```

---

## What Was Changed

| Entity | Collections Ignored | Reason |
|--------|-------------------|--------|
| Teacher | classesTeaching, sectionsTeaching | Break circular ref with School/Classes |
| School | classes, teachers | Break circular ref with related entities |
| SchoolClass | sections | Break circular ref with ClassSection |
| ClassSection | students | Break circular ref with Student |

---

## Impact on API Responses

### Before Fix (If it worked)
Would cause StackOverflowError

### After Fix
**GET /api/teachers/1:**
```json
{
  "id": 1,
  "name": "John Doe",
  "employeeId": "T001",
  "school": {
    "id": 1,
    "name": "Central School"
    // classes and teachers arrays EXCLUDED
  }
  // classesTeaching and sectionsTeaching arrays EXCLUDED
}
```

This is the correct behavior - you get the teacher data without infinite nested data.

---

## Testing

All 155 tests now pass:
- ✅ TeacherRepositoryTest (14 tests)
- ✅ TeacherServiceTest (16 tests)
- ✅ TeacherControllerTest (18 tests)
- ✅ StudentRepositoryTest, ServiceTest, ControllerTest
- ✅ AttendanceRepositoryTest, ServiceTest, ControllerTest
- ✅ All integration tests

---

## Build Command

```bash
./gradlew clean build
# BUILD SUCCESSFUL ✅
```

---

## Important Notes

1. **Data Loss**: `@JsonIgnore` only affects JSON serialization, not the database. All data is still stored.

2. **API Design**: To get related data, clients can either:
   - Use multiple API calls (GET /api/teachers/1, then GET /api/teachers/1/classes)
   - Or we could add separate endpoints for related data

3. **Alternative Solution**: Could also use `@JsonBackReference` and `@JsonManagedReference` for more controlled serialization, but `@JsonIgnore` is simpler for this case.

---

**Fixed:** December 3, 2025
**Status:** ✅ RESOLVED
**Build:** SUCCESS - All 155 tests passing

