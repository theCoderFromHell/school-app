# Swagger API Documentation

This application includes Swagger/OpenAPI documentation for all REST endpoints.

## Accessing Swagger UI

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

Or directly access the OpenAPI JSON specification at:

```
http://localhost:8080/v3/api-docs
```

## Features

- **Interactive API Documentation**: Test all endpoints directly from the browser
- **Request/Response Examples**: See sample data for all endpoints
- **Schema Definitions**: View all data models and their properties
- **Authentication Support**: Swagger UI is configured to work with the security settings

## Configuration

The Swagger configuration is defined in:
- `src/main/java/com/schoolapp/config/OpenApiConfig.java` - API metadata and server configuration
- `src/main/resources/application.properties` - SpringDoc settings

### Key Settings

```properties
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
```

## Using Swagger UI

1. **Start the application**: `./mvnw spring-boot:run`
2. **Open browser**: Navigate to `http://localhost:8080/swagger-ui.html`
3. **Explore endpoints**: Click on any endpoint to see details
4. **Try it out**: Use the "Try it out" button to execute requests
5. **View responses**: See the actual response data and status codes

## API Documentation Annotations

The controllers use OpenAPI 3.0 annotations:

- `@Tag` - Groups related endpoints
- `@Operation` - Describes an endpoint
- `@ApiResponses` - Documents possible response codes
- `@Parameter` - Describes path/query parameters
- `@Schema` - Defines model schemas

### Example from TeacherController

```java
@Tag(name = "Teacher Management", description = "APIs for managing teachers")
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    
    @Operation(summary = "Get all teachers", 
               description = "Retrieve a list of all teachers")
    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        // implementation
    }
}
```

## Available API Groups

- **Teacher Management** - CRUD operations for teachers
- **Student Management** - CRUD operations for students
- **Attendance Management** - Track and manage attendance
- **School Management** - Manage school information
- **Class Management** - Manage classes and sections

## Security Note

Swagger UI endpoints are publicly accessible (no authentication required) to facilitate API testing and documentation. In production, you may want to restrict access to these endpoints.

To disable Swagger in production, set:

```properties
springdoc.swagger-ui.enabled=false
```

## Customization

You can customize the API documentation by editing `OpenApiConfig.java`:

- Change API title and description
- Add contact information
- Configure servers (development, staging, production)
- Add security schemes
- Configure additional metadata

## Resources

- [SpringDoc OpenAPI Documentation](https://springdoc.org/)
- [OpenAPI 3.0 Specification](https://swagger.io/specification/)
- [Swagger UI](https://swagger.io/tools/swagger-ui/)
