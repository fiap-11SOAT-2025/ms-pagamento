# ===============================
# Build Stage
# ===============================
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar arquivos de configuração primeiro para cache otimizado
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline

# Copiar o restante do projeto
COPY src ./src

# Compilar o projeto (sem rodar testes)
RUN ./mvnw clean package -DskipTests

RUN apt-get update && apt-get install -y curl

# ===============================
# Runtime Stage
# ===============================
FROM eclipse-temurin:21-jdk

ARG APP_NAME=api-pagamento
ARG APP_PORT=8080

WORKDIR /app

COPY --from=builder /app/target/*.jar ${APP_NAME}.jar

# Expor a porta configurada
EXPOSE ${APP_PORT}

# Variáveis de ambiente padrão (podem ser sobrescritas no docker-compose)
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""
ENV MP_USER_ID=""
ENV MP_EXTERNAL_POS_ID=""
ENV MP_TOKEN=""
ENV MS_PEDIDO_HOST=""
ENV MS_PRODUCAO_HOST=""

ENTRYPOINT ["java", "-jar"]

# Comando de execução
ENTRYPOINT sh -c "java -jar \
    -Dserver.port=${APP_PORT} \
    -Dspring.datasource.url=${DB_URL} \
    -Dspring.datasource.username=${DB_USERNAME} \
    -Dspring.datasource.password=${DB_PASSWORD} \
    -DMP_USER_ID=${MP_USER_ID} \
    -DMP_EXTERNAL_POS_ID=${MP_EXTERNAL_POS_ID} \
    -DMP_TOKEN=${MP_TOKEN} \
    -DMS_PEDIDO_HOST=${MS_PEDIDO_HOST} \
    -DMS_PRODUCAO_HOST=${MS_PRODUCAO_HOST} \
    ${APP_NAME}.jar"
