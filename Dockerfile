
# Utiliza una imagen base de Java 11
FROM maven:3.8.1-openjdk-17-slim AS builder

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias de Maven
COPY pom.xml .

# Copiar el resto del proyecto
COPY . .

# Compilar y empaquetar la aplicación Spring Boot
RUN mvn clean package -DskipTests

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicia
ENTRYPOINT ["java","-jar","target/enrollment-service-1.0.0.jar"]