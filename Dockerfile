# ----------------------------------------------------------------------
# Multi-stage Dockerfile for Spring Boot 3+ / Java 17+
# ----------------------------------------------------------------------

# --- Stage 1: Build ---
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /workspace

# Cache Maven dependencies separately from source for faster rebuilds
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -q

# Copy source and build
COPY src src
RUN ./mvnw clean package -DskipTests -q

# Extract layered JAR for optimized layer caching
RUN java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

# --- Stage 2: Runtime ---
FROM eclipse-temurin:17-jre-alpine AS runtime

# Security: Run as non-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
WORKDIR /app

# Copy layered exploded JAR (maximizes Docker layer reuse)
COPY --from=builder --chown=appuser:appgroup /workspace/target/extracted/dependencies/         ./
COPY --from=builder --chown=appuser:appgroup /workspace/target/extracted/spring-boot-loader/    ./
COPY --from=builder --chown=appuser:appgroup /workspace/target/extracted/snapshot-dependencies/ ./
COPY --from=builder --chown=appuser:appgroup /workspace/target/extracted/application/           ./

EXPOSE 8080

# JVM options: Container-aware memory sizing
ENV JAVA_OPTS="-XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:InitialRAMPercentage=50.0 \
    -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]