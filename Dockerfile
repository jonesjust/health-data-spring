# Build stage
FROM openjdk:22-jdk-slim AS build
WORKDIR /app
COPY . ./
RUN ./gradlew build --no-daemon
RUN ./gradlew bootJar --no-daemon

# Run stage
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/api-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
