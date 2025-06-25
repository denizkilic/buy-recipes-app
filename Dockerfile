# ---- Build the application ----
FROM gradle:8.10-jdk17 AS build

WORKDIR /app

COPY --chown=gradle:gradle . .

RUN ./gradlew build --no-daemon

# ---- Run the application ----
FROM eclipse-temurin:24-jdk

WORKDIR /app

COPY --from=build /app/build/libs/buy-recipes-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
