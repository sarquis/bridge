# A imagem base.
FROM eclipse-temurin:17-jre

# Diretório de trabalho.
WORKDIR /app

# Copiando o arquivo JAR gerado pelo Maven para dentro do contêiner.
COPY target/bridge-*.jar /app/bridge.jar

# Expondo a porta em que a aplicação será executada.
EXPOSE 8080

# Executando a aplicação.
CMD ["java", "-jar", "/app/bridge.jar"]