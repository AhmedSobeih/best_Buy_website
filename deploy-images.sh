echo "Logging in to Docker Hub..."
docker login -u moeshiba -p Mohamed123
images=("authenticationapp-authentication_app" "messageapp-message_app" "productsapp-products_app" "transactionapp-transactions_app" "reviews-reviews_app" "userapp-user_app")

for image in "${images[@]}"; do
    docker tag $image moeshiba/$image
    docker repo create moeshiba/$image
    docker push moeshiba/$image
done