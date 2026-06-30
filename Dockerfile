# syntax=docker/dockerfile:1.7

FROM maven:3.9.11-eclipse-temurin-25 AS build

WORKDIR /workspace
COPY pom.xml .
COPY src ./src

RUN mvn -B -ntp -DskipTests clean package -Pproduction


FROM eclipse-temurin:25-jre

WORKDIR /app

ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"

COPY --from=build /workspace/target/quarkus-app/ ./

USER 1001

EXPOSE 8080

CMD ["sh", "-c", "exec java $JAVA_OPTS -Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT:-8080} -jar /app/quarkus-run.jar"]
