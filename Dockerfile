FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/API_ASSOCIACAO-1.0.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
