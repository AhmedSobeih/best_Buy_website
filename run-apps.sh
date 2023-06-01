#!/bin/bash
echo "Logging in to Docker Hub..."
docker login -u moeshiba -p Mohamed123
apps=("authenticationApp" "messageApp" "productsApp" "TransactionApp" "productReviewsApp/reviews" "userApp")
images=("authenticationapp-authentication_app" "messageapp-message_app" "productsapp-products_app" "transactionapp-transactions_app" "reviews-reviews_app" "userapp-user_app")

docker compose up --build -d #builds the container of rabbitmq server

pushd .

for app in "${apps[@]}"; do
    cd ./$app
    mvn clean package -DskipTests
    docker compose up --build -d
    popd
    pushd .
done

for image in "${images[@]}"; do
    docker tag $image moeshiba/$image
    docker repo create moeshiba/$image
    docker push moeshiba/$image
done