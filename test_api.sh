#!/bin/bash

echo "Adding a student..."
curl -X POST -H "Content-Type: application/json" -d '{"name": "John Doe", "rollNumber": "A001", "email": "john@example.com"}' http://localhost:8080/api/students
echo -e "\n"

echo "Getting all students..."
curl http://localhost:8080/api/students
echo -e "\n"

echo "Marking attendance..."
curl -X POST -H "Content-Type: application/json" -d '{"studentId": 1, "date": "2023-10-27", "status": "PRESENT"}' http://localhost:8080/api/attendance
echo -e "\n"

echo "Getting attendance by date..."
curl http://localhost:8080/api/attendance/date/2023-10-27
echo -e "\n"

echo "Getting attendance by student..."
curl http://localhost:8080/api/attendance/student/1
echo -e "\n"
