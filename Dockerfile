FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

ENV JAVA_OPTS="-Xms512m -Xmx3g -XX:+UseG1GC -XX:+TieredCompilation -XX:+ExitOnOutOfMemoryError"

COPY --from=build /app/target/API_ASSOCIACAO-1.0.jar /app/app.jar
EXPOSE 8080

CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]