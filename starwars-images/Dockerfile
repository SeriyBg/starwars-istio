FROM openjdk:11-jre-slim

COPY target/starwars-images-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/app.jar"]