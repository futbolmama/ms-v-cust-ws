# Step 1: Use a Maven image to build the Spring Boot application
FROM maven:3.9.4-eclipse-temurin-21 AS build

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the pom.xml and the rest of the project files to the container
COPY pom.xml .
COPY src ./src

# Step 4: Build the project
RUN mvn clean package -DskipTests

# Step 5: Use a minimal JDK image to run the Spring Boot app
FROM eclipse-temurin:17-jdk-jammy

# Step 6: Set the working directory for the runtime container
WORKDIR /app

# Step 7: Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Step 8: Expose the port that the Spring Boot app will run on
EXPOSE 8080

# Step 9: Define the entrypoint for the container to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
