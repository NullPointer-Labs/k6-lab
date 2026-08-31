FROM gradle:9.6.0-jdk21-alpine AS builder
WORKDIR /build
COPY build.gradle settings.gradle gradle.properties ./
COPY src ./src
RUN gradle clean build -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /build/build/quarkus-app/lib/ /app/lib/
COPY --from=builder /build/build/quarkus-app/*.jar /app/
COPY --from=builder /build/build/quarkus-app/app/ /app/app/
COPY --from=builder /build/build/quarkus-app/quarkus/ /app/quarkus/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]