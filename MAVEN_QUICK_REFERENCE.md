# Quick Reference - System Maven Commands

**Your Setup**: Maven 3.9.6 (System) + Java 21

---

## 🚀 Most Common Commands

```bash
# Build everything
mvn clean install

# Run the application
mvn spring-boot:run

# Run tests only
mvn test

# Package without tests (faster)
mvn package -DskipTests

# Clean build directory
mvn clean
```

---

## 🔧 With Java 21 Auto-Set

If you want Java 21 to be set automatically, use the helper script:

```bash
# Any Maven command works
./mvnw-java21.sh clean install
./mvnw-java21.sh spring-boot:run
./mvnw-java21.sh test
```

---

## ⚡ Quick Build Script

```bash
# One command to build everything (sets Java 21, builds, shows results)
./build-fix.sh
```

---

## 📝 Manual Java 21 Setup

If you want to use `mvn` directly with Java 21:

```bash
# Set once per terminal session
export JAVA_HOME=$(/usr/libexec/java_home -v 21)

# Then use mvn normally
mvn clean install
mvn spring-boot:run
```

---

## 🎯 Your Preference

**Primary**: `mvn` commands  
**Helper**: `./mvnw-java21.sh` when you want Java 21 set automatically  
**Quick**: `./build-fix.sh` for full automated build

---

## ✅ No Wrapper Needed

You have Maven installed system-wide, so you can use `mvn` directly without the `./mvnw` wrapper.

