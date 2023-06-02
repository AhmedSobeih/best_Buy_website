#!/bin/bash
#apps=("authenticationApp" "messageApp" "productsApp" "TransactionApp" "productReviewsApp/reviews" "userApp")
apps=("authenticationApp" "messageApp")
docker compose up --build -d #builds the container of rabbitmq server

pushd .

for app in "${apps[@]}"; do
    cd ./$app
    mvn clean package -DskipTests
    docker compose up --build -d
    popd
    pushd .
done
