FROM maven:3.6.3-openjdk-17 AS build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=build target/work3-1.0.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]
