# Usando a imagem oficial do OpenJDK como base.
FROM openjdk:17-jdk-slim

# Definindo o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copiando o arquivo JAR da aplicação para o contêiner.
COPY target/bridge.jar /app/bridge.jar

# Expondo a porta em que a aplicação irá rodar.
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/bridge.jar"]