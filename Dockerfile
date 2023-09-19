FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar HealthyMeet.jar
CMD ["java", "-jar", "HealthyMeet.jar"]

