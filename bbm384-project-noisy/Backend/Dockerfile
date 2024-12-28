# Use the official OpenJDK 17 base image
FROM eclipse-temurin:21-alpine AS builder

WORKDIR /opt/app

COPY gradlew ./
COPY gradle/ ./gradle/

COPY build.gradle settings.gradle ./

RUN ./gradlew --no-daemon --console=plain dependencies

COPY ./src ./src

RUN ./gradlew --no-daemon --console=plain clean build -x test

FROM eclipse-temurin:21-alpine

WORKDIR /opt/app

COPY src/main/resources/.env ./

EXPOSE 8080

COPY --from=builder /opt/app/build/libs/*.jar /opt/app/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
