FROM openjdk:11-jre-slim

COPY target/starwars-quotes-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
