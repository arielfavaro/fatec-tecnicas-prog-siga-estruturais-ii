# Imagem base com OpenJDK 17 (Eclipse Temurin)
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia os fontes para o container
COPY src ./src

# Compila as classes Java para a pasta bin
RUN javac -d bin src/siga/*.java

# Executa a classe principal
CMD ["java", "-cp", "bin", "siga.Main"]
