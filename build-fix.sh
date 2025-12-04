#!/bin/bash
# Build Fix Script for school-app
# This script sets up the environment and runs the Maven build

echo "================================"
echo "School App - Maven Build Script"
echo "================================"
echo ""

# Set Java 21
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
echo "✓ JAVA_HOME set to: $JAVA_HOME"

# Verify Java version
java_version=$($JAVA_HOME/bin/java -version 2>&1 | head -n 1)
echo "✓ Java Version: $java_version"
echo ""

# Navigate to project directory
cd "$(dirname "$0")"
echo "✓ Project directory: $(pwd)"
echo ""

# Clean build
echo "Starting Maven clean install..."
echo "--------------------------------"
mvn clean install

# Check exit code
if [ $? -eq 0 ]; then
    echo ""
    echo "================================"
    echo "✓ BUILD SUCCESS!"
    echo "================================"
    echo ""
    echo "To run the application:"
    echo "  ./mvnw spring-boot:run"
    echo ""
else
    echo ""
    echo "================================"
    echo "✗ BUILD FAILED!"
    echo "================================"
    echo ""
    echo "Common fixes:"
    echo "  1. Check Java version: java -version"
    echo "  2. Try: ./mvnw clean compile"
    echo "  3. Check errors above"
    echo ""
    exit 1
fi

