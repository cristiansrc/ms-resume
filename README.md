# API ms-resume – cristiansrc.com

Backend Java Spring Boot para la gestión y exposición del currículum, experiencia profesional, habilidades, proyectos y blog tecnológico del sitio [cristiansrc.com](https://cristiansrc.com).

Este proyecto provee una API RESTful para la administración centralizada de toda la información de la página personal, incluyendo descargas de CV en PDF, integración de imágenes, videos, etiquetas, habilidades, proyectos destacados y artículos de blog.  
La arquitectura y el diseño siguen las mejores prácticas REST, OpenAPI 3.1.0 y facilitan la generación automática de documentación y clientes.

---

## Características

- **Spring Boot 3.5.3**, Java 21+
- **Maven** como sistema de construcción
- **PostgreSQL 16.9** como base de datos relacional
- API documentada con **OpenAPI 3.1.0 (Swagger)**
- Descarga directa del CV en formato PDF
- Manejo robusto de errores y validaciones
- Relacionamiento anidado en entidades clave
- Endpoints para imágenes, videos, etiquetas, habilidades, proyectos y blog con paginación
- Preparado para despliegue en Docker, Heroku, AWS u otros proveedores

---

## Estructura de carpetas

```
src/
  main/
    java/
      com/
        cristiansrc/
          api/
            controller/
            model/
            dto/
            service/
            repository/
            exception/
            config/
    resources/
      application.properties
      db/
        migration/
          V1__CREATE-basic_data.sql
          V2__CREATE-blog.sql
          V3__CREATE-image_url.sql
          V4__CREATE-video_url.sql
          V5__CREATE-label.sql
          V6__CREATE-skill_type.sql
          V7__CREATE-skill.sql
          V8__CREATE-skill_son.sql
          V9__CREATE-futured_project.sql
          V10__CREATE-home.sql
          V11__CREATE-curriculum.sql
          V12__CREATE-curriculum_pdf.sql
          V13__CREATE-curriculum_pdf_content.sql
          V14__CREATE-curriculum_pdf_content_image.sql
          V15__CREATE-curriculum_pdf_content_video.sql
      application.properties
      application-local.properties
      application-test.properties
      openapi.yaml
  test/
```

---

## Endpoints principales

| Entidad          | Método(s)    | Endpoint                       | Descripción                                   |
|------------------|-------------|-------------------------------|-----------------------------------------------|
| Basic Data       | GET, PUT     | `/basic-data/{id}`            | Consultar o actualizar datos personales       |
| ImageUrl         | GET, POST, GET(id), DELETE | `/image-url`, `/image-url/{id}` | Gestión de imágenes del portal                |
| VideoUrl         | GET, POST, GET(id), DELETE | `/video-url`, `/video-url/{id}` | Gestión de videos (YouTube)                   |
| Label            | GET, POST, GET(id), DELETE | `/label`, `/label/{id}`           | Gestión de etiquetas                          |
| Home             | GET, PUT     | `/home/{id}`                  | Gestión de la información del home            |
| Blog             | GET(pag), POST, GET(id), PUT, DELETE | `/blog`, `/blog/{id}` | Artículos de blog con paginación              |
| SkillType        | GET, POST, GET(id), PUT, DELETE | `/skill-type`, `/skill-type/{id}` | Tipos de habilidad y sus habilidades          |
| Skill            | GET, POST, GET(id), PUT, DELETE | `/skill`, `/skill/{id}` | Habilidades y especializaciones               |
| SkillSon         | GET, POST, GET(id), PUT, DELETE | `/skill-son`, `/skill-son/{id}` | Especializaciones de habilidades              |
| FuturedProject   | GET, POST, GET(id), PUT, DELETE | `/futured-project`, `/futured-project/{id}` | Proyectos destacados                          |
| Curriculum (PDF) | GET         | `/curriculum`                 | Descargar CV en formato PDF                   |

---

## Instalación y configuración

### **Requisitos previos**

- Java 21 o superior
- Maven 3.9.9+
- PostgreSQL 16.9

### **Clonar el repositorio**

```sh
git clone https://github.com/tuusuario/cristiansrc-api.git
cd cristiansrc-api
```

### **Configuración de base de datos**

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.application.name=ms-resume
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration,classpath:db/callback
spring.datasource.url=${DB_URL}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
spring.jpa.open-in-view=false
logging.level.root=WARN
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
```

### **Ejecutar migraciones (Flyway)**

Asegúrate que el usuario tenga permisos de creación/modificación de tablas.

### **Construcción y ejecución**

```sh
mvn clean install
mvn spring-boot:run
```

El API estará disponible en `http://localhost:8080`.

---

## Documentación y pruebas

La documentación Swagger/OpenAPI estará disponible en:

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

El archivo de definición OpenAPI 3.1.0 se encuentra en:  
`src/main/resources/openapi.yaml`

---

## Ejemplo de petición (descarga del curriculum PDF)

```http
GET /curriculum HTTP/1.1
Host: localhost:8080
Accept: application/pdf

# Respuesta: PDF binario, Content-Disposition: attachment; filename="curriculum-cristiansrc.pdf"
```

---

## Estándar de respuestas de error

Las respuestas de error siguen el siguiente formato JSON:

```json
{
  "timestamp": "2025-07-15T12:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "El campo 'email' es obligatorio.",
  "path": "/basic-data/1"
}
```

---

## Contribuir

1. Haz un fork del repositorio
2. Crea una rama: `git checkout -b feature/nueva-funcionalidad`
3. Realiza tus cambios y asegúrate de pasar los tests
4. Envía tu pull request

---

## Licencia

Este proyecto está bajo la licencia MIT.  
Copyright (c) 2025

---

## Contacto

Desarrollado por [Cristhiam Reina](https://cristiansrc.com)  
Email: cristiansrc@gmail.com

---
