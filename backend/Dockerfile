FROM openjdk:19-jdk-slim
VOLUME /tmp
EXPOSE 8080

COPY --from=build /output/artifacts/backend_jar/backend.jar backend.jar

ENTRYPOINT ["java", "-jar", "/backend.jar"]
EXPOSE 8080