# Stage 1: build
# Start with a Maven image that includes JDK 21
FROM maven:3.9.10-amazoncorretto-24 AS build

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Chạy Spotless để format code
RUN mvn spotless:apply -q

# Build source code with maven
RUN mvn clean package -DskipTests -Dspotless.skip

#Stage 2: create image
# Start with Amazon Correto JDK 21
FROM amazoncorretto:24

# Set working folder to App and copy complied file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# 
docker compose up -d
# Check docker sure is running
# docker compose -v up -d