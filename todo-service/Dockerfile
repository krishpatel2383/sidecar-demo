FROM maven:3.9.6-eclipse-temurin-21 as build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
RUN mkdir -p /app/logs

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
