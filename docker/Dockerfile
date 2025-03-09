FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ../target/consumer-usuario-gambalonga-sys-0.0.1-SNAPSHOT.jar /app/consumer-usuario-gambalonga-sys-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "consumer-usuario-gambalonga-sys-0.0.1-SNAPSHOT.jar"]
