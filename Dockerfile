FROM eclipse-temurin:17-jdk
COPY target/cat-api-app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]