FROM openjdk:19-jdk-slim

COPY target/*.jar backend.jar

ENTRYPOINT ["java", "-jar", "/backend.jar"]
EXPOSE 8080