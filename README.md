# Role Service

Microservicio de gestión de roles y páginas. Expone una API REST para crear, consultar, actualizar y eliminar roles, y publica eventos de dominio en Kafka con esquemas Avro registrados en Confluent Schema Registry.

## Estructura del proyecto

```
rol_service/
├── role-service-events/   # Esquemas Avro + artefacto Maven publicable en GitHub Packages
└── role-service-core/     # Lógica de negocio, API REST y adaptadores de infraestructura
```

| Módulo | Descripción |
|--------|-------------|
| `role-service-events` | Define los esquemas Avro (`RoleCreatedEvent`, `RoleUpdatedEvent`, `RoleDeletedEvent`) y genera las clases Java. Se publica como dependencia Maven en GitHub Packages. |
| `role-service-core` | Aplicación Spring Boot. Gestiona el dominio `Role` / `Page`, persiste en PostgreSQL y produce eventos Kafka. |

## Tecnologías

- Java 21 · Spring Boot 3.5
- PostgreSQL 16
- Apache Kafka + Confluent Schema Registry (Avro 1.12)
- MapStruct · Lombok
- Maven Wrapper

## Requisitos previos

- JDK 21
- Docker y Docker Compose
- Acceso a GitHub Packages (para resolver `sergioricart-microservice-commons`)

## Configuración local

### 1. Credenciales Maven

Crea o edita `~/.m2/settings.xml` con tu Personal Access Token de GitHub (scope `read:packages`):

```xml
<settings>
  <servers>
    <server>
      <id>sergioricart-commons</id>
      <username>TU_USUARIO_GITHUB</username>
      <password>TU_GITHUB_TOKEN</password>
    </server>
  </servers>
</settings>
```

### 2. Variables de entorno

El módulo `role-service-core` lee su configuración desde `role-service-core/.env`. Crea el archivo copiando el siguiente ejemplo:

```dotenv
DB_USER=sricart
DB_PASSWORD=<contraseña>
DB_NAME=role_db
DB_HOST=localhost
DB_PORT=5434

KAFKA_BOOTSTRAP_SERVERS=localhost:9092
SCHEMA_REGISTRY_URL=http://localhost:8081

SERVER_PORT=8090
```

### 3. Levantar la base de datos

```bash
docker compose up -d
```

Esto inicia un contenedor PostgreSQL 16 en el puerto definido en `.env`.

### 4. Compilar y ejecutar

```bash
./mvnw clean install -pl role-service-events
./mvnw spring-boot:run -pl role-service-core
```

La aplicación arranca en `http://localhost:8090`.

## API REST

### Roles — `/api/v1/role`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/role/create` | Crea un rol |
| `GET` | `/api/v1/role` | Lista todos los roles |
| `GET` | `/api/v1/role/{id}` | Obtiene un rol por ID |
| `PATCH` | `/api/v1/role/{id}` | Actualiza un rol |
| `DELETE` | `/api/v1/role/{id}` | Elimina un rol |

### Páginas — `/api/v1/page`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/v1/page/role/{roleId}` | Lista las páginas asignadas a un rol |

### Ejemplos

**Crear rol**
```bash
curl -X POST http://localhost:8090/api/v1/role/create \
  -H "Content-Type: application/json" \
  -d '{"name": "ADMIN", "description": "Administrador del sistema"}'
```

**Listar roles**
```bash
curl http://localhost:8090/api/v1/role
```

## Eventos Kafka

Cada operación de escritura publica un evento Avro en Kafka:

| Evento | Trigger |
|--------|---------|
| `RoleCreatedEvent` | Creación de un rol |
| `RoleUpdatedEvent` | Actualización de un rol |
| `RoleDeletedEvent` | Eliminación de un rol |

Los esquemas `.avsc` se encuentran en `role-service-events/src/main/resources/schema.role/`.

## CI/CD

| Workflow | Trigger | Acción |
|----------|---------|--------|
| **CI** (`ci.yml`) | Pull request a `main` o `development` | Ejecuta `./mvnw clean test` contra PostgreSQL en contenedor |
| **CD** (`cd.yml`) | Push a `main` o `development` | Publica `role-service-events` en GitHub Packages vía `./mvnw deploy` |

Requiere el secret `GH_PACKAGES_TOKEN` configurado en el repositorio con scope `write:packages`.

## Tests

```bash
./mvnw clean test -pl role-service-events,role-service-core --also-make
```