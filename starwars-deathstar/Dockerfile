FROM openjdk:11-jre-slim

COPY target/starwars-deathstar-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
