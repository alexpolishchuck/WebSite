FROM openjdk:19-jdk-slim
VOLUME /tmp
EXPOSE 8080

COPY target/*.jar backend.jar

ENTRYPOINT ["java", "-jar", "/backend.jar"]
EXPOSE 8080