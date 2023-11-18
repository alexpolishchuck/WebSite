FROM openjdk:19-jdk-slim

VOLUME /tmp

COPY target/*.jar backend.jar

ENTRYPOINT ["java", "-jar", "/backend-0.0.1-SNAPSHOT.jar"]
EXPOSE 8087