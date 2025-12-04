# Using System Maven Instead of Maven Wrapper

**Date**: December 3, 2025  
**Preference**: System Maven (3.9.6)

---

## ✅ Your System Maven Setup

**Maven Version**: 3.9.6  
**Location**: `/Users/theCoderFromHell/Development/tools/apache-maven-3.9.6`  
**Status**: Already configured in PATH via `.bash_profile`

---

## 🔧 Changes Made

### 1. Maven Wrapper Files
The Maven wrapper files can be safely removed if you prefer:
- `.mvn/wrapper/` directory
- `mvnw` script
- `mvnw.cmd` script

**Note**: These files are kept in the project for team members who don't have Maven installed, but you can use `mvn` commands directly.

### 2. Updated Build Scripts

Your helper scripts now use system Maven:

**build-fix.sh**:
```bash
# Set Java 21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)

# Use system Maven
mvn clean install
```

**mvnw-java21.sh** (now uses system Maven):
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn "$@"
```

---

## 📝 Build Commands (Using System Maven)

```bash
# Build the project
mvn clean install

# Or with Java 21 explicitly set
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
mvn clean install

# Run application
mvn spring-boot:run

# Run tests
mvn test

# Package without tests
mvn package -DskipTests

# Clean
mvn clean
```

---

## 🎯 Recommended Approach

Since you have Maven 3.9.6 installed system-wide, you can use `mvn` directly:

```bash
# Simple - just use mvn
cd ~/Development/projects/school-app
mvn clean install
```

**Advantages for you**:
- ✅ Faster (no wrapper overhead)
- ✅ Uses familiar `mvn` command
- ✅ Consistent with other projects on your machine
- ✅ No need to remember `./mvnw`

---

## ⚠️ Team Considerations

If you're working with a team, keep in mind:

**Keep wrapper files** (`.mvn/`, `mvnw`, `mvnw.cmd`) in the repository because:
- Other team members might not have Maven installed
- CI/CD pipelines can use the wrapper
- Ensures version consistency across environments

**You personally** can use `mvn` commands, while others use `./mvnw` - both work!

---

## 🗑️ Optional: Remove Maven Wrapper

If you want to completely remove the wrapper from your project:

```bash
cd ~/Development/projects/school-app

# Remove wrapper files
rm -rf .mvn/wrapper/
rm mvnw mvnw.cmd

# Update .gitignore to ignore wrapper if it gets regenerated
echo ".mvn/wrapper/" >> .gitignore
echo "mvnw" >> .gitignore
echo "mvnw.cmd" >> .gitignore
```

**Warning**: This will require all team members to have Maven installed.

---

## 📋 Updated Documentation References

All documentation will now show both options:

```bash
# Option 1: System Maven (Your preference)
mvn clean install

# Option 2: Maven Wrapper (For team members)
./mvnw clean install
```

---

## ✅ Summary

**Your Setup**:
- Use `mvn` commands directly
- Maven 3.9.6 already installed and configured
- No need to use `./mvnw` prefix

**Commands**:
```bash
mvn clean install          # ✅ Your preference
export JAVA_HOME=$(/usr/libexec/java_home -v 21) && mvn clean install  # With Java 21
```

**Helper Scripts Updated**:
- `build-fix.sh` now uses `mvn`
- `mvnw-java21.sh` now calls `mvn` instead of `./mvnw`

You're all set to use system Maven! 🚀

