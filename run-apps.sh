#!/bin/bash

apps=("authenticationApp" "messageApp" "productsApp" "TransactionApp" "productReviewsApp/reviews")

for app in "${apps[@]}"; do
    cd ./$app
    mvn clean package -DskipTests
    docker compose up --build -d
    cd ../
done