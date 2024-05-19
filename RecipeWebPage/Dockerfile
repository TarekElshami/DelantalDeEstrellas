#################################################
# Base image for the build container
################################################# 3.8.5
FROM maven:3.9.6-eclipse-temurin-17 as builder

# Define the working directory where commands will be executed
WORKDIR /project

# Copy the project dependencies
COPY pom.xml /project/

# Download the project dependencies
RUN mvn clean verify

# Copy the project code
COPY src /project/src

# Build the project
RUN mvn package -o -DskipTests=true

#################################################
# Base image for the application container
#################################################
FROM openjdk:17

# Define the working directory where the JAR is located
WORKDIR /usr/app/

# Copy the JAR from the build container
COPY --from=builder /project/target/*.jar /usr/app/

# Download the cockroach certificate
RUN curl --create-dirs -o ~/.postgresql/root.crt -O https://cockroachlabs.cloud/clusters/94285693-fa2e-4a98-a3ab-589ceb61e17a/cert

# Indicate the port that exposed by the container
EXPOSE 8080

# Command that is executed when running docker run
CMD [ "java", "-jar", "app.jar" ]