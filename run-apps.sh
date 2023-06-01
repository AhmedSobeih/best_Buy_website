#!/bin/bash

apps=("authenticationApp" "messageApp" "productsApp" "TransactionApp" "productReviewsApp/reviews" "userApp")

pushd .

for app in "${apps[@]}"; do
    cd ./$app
    mvn clean package -DskipTests
    docker compose up --build -d
    popd
    pushd .
done