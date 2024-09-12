FROM gradle:8.10.1-jdk11 AS builder
WORKDIR /app

COPY ../../Services/gradlew /app/
COPY ../../Services/gradle /app/gradle/
COPY ../../Services/build.gradle /app/
COPY ../../Services/settings.gradle /app/
COPY ../../Services/CoreService /app/CoreService
COPY ../../Services/Common /app/Common

RUN chmod +x ./gradlew

RUN ./gradlew :CoreService:assemble --no-daemon



FROM openjdk:11-jre-slim AS package
WORKDIR /app

COPY --from=builder /app/CoreService/build/libs/*.jar /app/core-service.jar

CMD ["java", "-jar", "/app/core-service.jar"]

