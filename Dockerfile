# ==========================================
# ETAPA 1: BUILD (El Constructor) 🏗️
# ==========================================
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copiamos el POM y el código fuente
# OJO: Si tu 'openapi.yml' está en la raíz del proyecto, asegúrate de copiarlo también.
# Al hacer COPY . . copiamos TODO (menos lo que esté en .dockerignore)
COPY . .

# 2. Compilamos y generamos el JAR
# Usamos 'clean package' que es suficiente para generar el jar en target/
# Esto ejecutará también el plugin de openapi-generator si está atado al ciclo de vida 'generate-sources'
RUN mvn clean package -DskipTests

# ==========================================
# ETAPA 2: RUN (El Ejecutor) 🚀
# ==========================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 1. Copiamos SOLO el JAR final desde la etapa de construcción
# El asterisco *.jar es clave para que no importe si cambia la versión (0.0.1, 0.0.2...)
COPY --from=build /app/target/*.jar app.jar

# 2. Variables de entorno básicas (se pueden sobrescribir desde docker-compose)
ENV SPRING_PROFILES_ACTIVE=prod

# 3. Comando de arranque
# Nota: La RAM se limita en el docker-compose con JAVA_TOOL_OPTIONS
ENTRYPOINT ["java", "-jar", "app.jar"]