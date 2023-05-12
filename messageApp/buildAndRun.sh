#!/bin/bash

# Step 1: Run Docker Compose Up
echo "Step 1: Running Docker Compose Up"
docker-compose up -d

# Step 2: Run Maven Clean Build
echo "Step 2: Running Maven Clean Package"
mvn clean package

# Step 3: Run Maven Run
# Step 3: Run Application
echo "Step 3: Running Application"
mvn exec:java -Dexec.mainClass="com.FAM.messageApp.MessageAppApplication"
