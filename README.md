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

<img width="623" height="112" alt="Captura de pantalla 2025-09-07 163440" src="https://github.com/user-attachments/assets/68a1c152-88b3-4aec-9345-4389a3047df9" />

The app will start at **http://localhost:8080**.  
La app iniciará en **http://localhost:8080**.

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

<img width="1367" height="784" alt="Captura de pantalla 2025-09-07 164513" src="https://github.com/user-attachments/assets/e881e0ee-0ae8-4ba2-8011-b4ed1e107e3e" />
<img width="1378" height="640" alt="Captura de pantalla 2025-09-07 164521" src="https://github.com/user-attachments/assets/2507fb40-3891-47d9-84ee-ae2de4284f6c" />
<img width="1378" height="722" alt="Captura de pantalla 2025-09-07 164528" src="https://github.com/user-attachments/assets/fbede22e-ff83-4f2f-b120-c7c1c75b2c05" />
<img width="1368" height="937" alt="Captura de pantalla 2025-09-07 164547" src="https://github.com/user-attachments/assets/6a121da1-b4c1-4b61-b509-3036184d8b0b" />
<img width="1372" height="668" alt="Captura de pantalla 2025-09-07 164457" src="https://github.com/user-attachments/assets/1ff6e33c-7036-4132-8840-f9830be9f4d4" />

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
3. The collection uses a `baseUrl` variable: `http://localhost:8080`.

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
curl -X GET http://localhost:8080/lab/users

# Create
curl -X POST http://localhost:8080/lab/users   -H "Content-Type: application/json"   -d '{"name":"Ada Lovelace","email":"ada@demo.test"}'

# Replace <built-in function id> with a real id
curl -X GET http://localhost:8080/lab/users/{ID}

curl -X PUT http://localhost:8080/lab/users/{ID}   -H "Content-Type: application/json"   -d '{"name":"Ada L.","email":"ada@demo.test"}'

curl -X DELETE http://localhost:8080/lab/users/{ID}
```

---

## 8) Build a JAR / Construir JAR

```bash
mvn -DskipTests package
java -jar target/*.jar --spring.profiles.active=lab --server.port=8080
```

<img width="1890" height="1046" alt="Captura de pantalla 2025-09-07 165259" src="https://github.com/user-attachments/assets/f58c2e81-0410-44ac-9760-83019acf6363" />

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
