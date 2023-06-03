#!/bin/bash
apps=("authenticationApp" "messageApp" "productsApp" "TransactionApp" "productReviewsApp/reviews" "userApp")
# apps=("authenticationApp" "TransactionApp" "userApp")
docker compose up --build -d #builds the container of rabbitmq server

pushd .

for app in "${apps[@]}"; do
    cd ./$app
    mvn clean package -DskipTests
    docker compose up --build -d
    popd
    pushd .
done

# run the API gateway
docker build -t bestbuy_nginx_image .
docker run -d -p 8102:80 --network=bestbuy --name=bestbuy_nginx bestbuy_nginx_image:latest

