# Usamos una imagen ligera de Java 21 (Eclipse Temurin es la recomendada para prod)
FROM eclipse-temurin:21-jre-alpine

# Creamos un volumen temporal (buena práctica en Spring Boot)
VOLUME /tmp

# Copiamos el JAR que GitHub nos enviará
# NOTA: En el pipeline renombraremos tu jar largo a 'app.jar' para simplificar
COPY app.jar app.jar

# Configuración de inicio
ENTRYPOINT ["java","-jar","/app.jar"]