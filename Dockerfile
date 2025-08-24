FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/smart-commerce-api-0.0.1-SNAPSHOT.jar app.jar

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
