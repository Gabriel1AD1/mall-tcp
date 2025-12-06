FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiar el JAR generado en tu máquina
COPY build/libs/*.jar app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
