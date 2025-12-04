# Build Instructions

## Prerequisites

- Java 21 (JDK 21)
- Maven 3.9+

## Building the Project

### Option 1: Using System Maven (Recommended)

```bash
# Set Java 21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)

# Build
mvn clean install
```

### Option 2: Using the Helper Script

```bash
# Automatically sets Java 21 and runs Maven
./mvnw-java21.sh clean install
```

### Option 3: Quick Build Script

```bash
# Uses the automated build script
./build-fix.sh
```

## Common Commands

### Compile Only
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Package JAR
```bash
mvn clean package
```

### Run Application
```bash
mvn spring-boot:run
```

### With Helper Script
```bash
# All commands work with the helper script too
./mvnw-java21.sh clean compile
./mvnw-java21.sh test
./mvnw-java21.sh spring-boot:run
```

## Troubleshooting

### Java Version Issues

If you encounter errors like:
```
Fatal error compiling: java.lang.ExceptionInInitializerError: com.sun.tools.javac.code.TypeTag
```

This indicates a Java version mismatch. Ensure you're using Java 21:

```bash
java -version
# Should show: java version "21.x.x"
```

### Compiler Plugin Issues

The `pom.xml` has been configured with:
- Maven Compiler Plugin with `fork=true` to ensure proper Java 21 compilation
- Lombok 1.18.34 for compatibility with Java 21

## Project Structure

- **Language**: Java 21
- **Build Tool**: Maven
- **Framework**: Spring Boot 3.2.0
- **Database**: MySQL (Production), H2 (Tests)
- **Security**: Spring Security with OAuth2

## Database Configuration

The application uses MySQL in production. Connection details are in `src/main/resources/application.properties`.

For tests, H2 in-memory database is automatically used.

