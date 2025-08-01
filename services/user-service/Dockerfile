FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/user-service-1.0.0.jar user-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "user-service.jar"]