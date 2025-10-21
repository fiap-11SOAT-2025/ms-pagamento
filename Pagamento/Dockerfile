# ===============================
# Build Stage
# ===============================
FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar arquivos de configuração primeiro para cache otimizado
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
# Baixar dependências (melhora cache)
RUN ./mvnw dependency:go-offline

# Copiar o restante do projeto
COPY src ./src

# Compilar o projeto (sem rodar testes)
RUN ./mvnw clean package -DskipTests

# ===============================
# Runtime Stage
# ===============================
FROM openjdk:21-slim

# Argumentos de build
ARG APP_NAME=api-Pagamento
ARG APP_PORT=7774

# Certifique-se de limpar JARs antigos antes de mover o novo
WORKDIR /app
RUN rm -f *.jar
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

# Comando de execução
ENTRYPOINT sh -c "java -jar \
    -Dserver.port=${APP_PORT} \
    -Dspring.datasource.url=${DB_URL} \
    -Dspring.datasource.username=${DB_USERNAME} \
    -Dspring.datasource.password=${DB_PASSWORD} \
    -DMP_USER_ID=${MP_USER_ID} \
    -DMP_EXTERNAL_POS_ID=${MP_EXTERNAL_POS_ID} \
    -DMP_TOKEN=${MP_TOKEN} \
    ${APP_NAME}.jar"
