# ===============================
# Build Stage
# ===============================
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

# Cache de dependências
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# Compilação
COPY src ./src
RUN ./mvnw clean package -DskipTests

# ===============================
# Runtime Stage
# ===============================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Define nome padrão do JAR
ENV APP_JAR_NAME=ms-pagamento.jar

# Copia o JAR gerado
COPY --from=builder /app/target/*.jar /app/${APP_JAR_NAME}

# Porta padrão
EXPOSE 8080


# Entrypoint usando exec form para melhor gerenciamento de sinais
ENTRYPOINT ["sh", "-c", "java -jar /app/${APP_JAR_NAME}"]