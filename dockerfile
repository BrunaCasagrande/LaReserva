# Primeira etapa: Construir a aplicação
FROM maven:3.8.6-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn clean package


# Etapa 2: Construção da imagem final
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/lareserva-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]