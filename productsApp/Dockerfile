FROM eclipse-temurin:19-jdk-alpine
EXPOSE 8080
ADD target/products-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]