#!/bin/bash

apps=("authenticationApp" "messageApp" "productsApp" "TransactionApp")

for app in "${apps[@]}"; do
    cd ./$app
    mvn clean package -DskipTests
    docker compose up --build -d
    cd ../
done