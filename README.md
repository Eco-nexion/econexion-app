# Econexion Lab – In‑Memory Users API  
**(EN / ES)**

A tiny Spring Boot REST API used for lab practice. It exposes a CRUD for *users* backed by an in‑memory `ConcurrentHashMap` and is enabled only under the `lab` profile.

---

## 1) Overview / Resumen

**EN**: This module provides a minimal API to create, read, update and delete users. It is profile‑scoped (`lab`) so it won’t interfere with your main application.

**ES**: Este módulo expone un CRUD mínimo de usuarios en memoria. Está limitado al perfil `lab` para no interferir con tu aplicación principal.

---

## 2) Requirements / Requisitos
- Java **21**
- Maven **3.9+**
- Internet to download dependencies / Internet para descargar dependencias

---

## 3) Run / Ejecutar

**Bash / macOS / Linux**
```bash
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=lab,--server.port=8081"
```

**Windows PowerShell**
```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=lab,--server.port=8081"
```

The app will start at **http://localhost:8081**.  
La app iniciará en **http://localhost:8081**.

---

## 4) Endpoints (Users) / Endpoints (Usuarios)

Base path / Ruta base: `/lab/users`

| Method | Path              | Description (EN)                    | Descripción (ES)                 |
|-------:|-------------------|-------------------------------------|----------------------------------|
| GET    | `/lab/users`      | List all users                      | Lista todos los usuarios         |
| GET    | `/lab/users/{id}` | Get user by id                    | Obtiene un usuario por id        |
| POST   | `/lab/users`      | Create user                         | Crea un usuario                  |
| PUT    | `/lab/users/{id}` | Update user                       | Actualiza un usuario             |
| DELETE | `/lab/users/{id}` | Delete user                       | Elimina un usuario               |

### JSON samples / Ejemplos JSON

**POST – Create / Crear**
```json
{
  "name": "Ada Lovelace",
  "email": "ada@demo.test"
}
```

**PUT – Update / Actualizar**
```json
{
  "name": "Ada L.",
  "email": "ada@demo.test"
}
```

### Expected HTTP codes / Códigos esperados
- `200 OK` – success for GET/PUT (éxito en GET/PUT)
- `201 Created` – when creating (al crear)
- `204 No Content` – delete with no body (eliminar sin cuerpo)
- `404 Not Found` – id does not exist (id no existe)
- `400 Bad Request` – invalid input (entrada inválida)

---

## 5) Import Postman / Importar Postman

A ready‑to‑use collection is provided.  
Se incluye una colección lista para usar.

1. Open Postman → **Import** → *File*.  
2. Select `Econexion_Lab_Users_CRUD.postman_collection.json`.  
3. The collection uses a `baseUrl` variable: `http://localhost:8081`.

> If you prefer cURL examples, see Section 7.  
> Si prefieres cURL, ver Sección 7.

---

## 6) Project layout / Estructura del proyecto

```
src/main/java/io/econexion/lab/users/
  ├─ dto/
  │   ├─ UserDto.java
  │   ├─ UserCreateRequest.java
  │   └─ UserUpdateRequest.java
  ├─ InMemoryLabUserService.java   (@Service @Profile("lab"))
  ├─ LabUserService.java           (interface)
  └─ LabUserController.java        (@RestController @Profile("lab"))
```

If you use Spring Security, ensure `/lab/**` is permitted in `SecurityConfig`.  
Si usas Spring Security, permite `/lab/**` en `SecurityConfig`.

---

## 7) Quick cURL

```bash
# List
curl -X GET http://localhost:8081/lab/users

# Create
curl -X POST http://localhost:8081/lab/users   -H "Content-Type: application/json"   -d '{"name":"Ada Lovelace","email":"ada@demo.test"}'

# Replace <built-in function id> with a real id
curl -X GET http://localhost:8081/lab/users/{ID}

curl -X PUT http://localhost:8081/lab/users/{ID}   -H "Content-Type: application/json"   -d '{"name":"Ada L.","email":"ada@demo.test"}'

curl -X DELETE http://localhost:8081/lab/users/{ID}
```

---

## 8) Build a JAR / Construir JAR

```bash
mvn -DskipTests package
java -jar target/*.jar --spring.profiles.active=lab --server.port=8081
```

---

## 9) Notes / Notas
- No database is required (in‑memory store).  
  No se requiere base de datos (almacenamiento en memoria).
- For H2 console in other modules, allow `/h2-console/**` and disable frame‑options.  
  Para consola H2 en otros módulos, permitir `/h2-console/**` y deshabilitar frame‑options.

---

## 10) License / Licencia
MIT (or the license in your repo). / MIT (o la licencia de tu repositorio).

— Generated 2025-09-07 20:37:28
