# Econexion Lab – REST API (Users + Weather + JWT)
**(EN / ES)**

Spring Boot REST API for lab practice. It now includes:
- In‑memory CRUD for *users* (profile `lab`).
- Weather endpoint (`/v1/weather/{city}`) with JWT protection for POST.
- Basic login (`/api/auth/login`) issuing JWT tokens.

---

## 1) Overview / Resumen

**EN**: This lab project demonstrates Spring Boot REST with profiles, in‑memory persistence, and authentication using JWT.  
**ES**: Este proyecto de laboratorio demuestra REST con Spring Boot, perfiles, persistencia en memoria y autenticación con JWT.

---

## 2) Requirements / Requisitos
- Java **21**
- Maven **3.9+**
- Internet for dependencies / Internet para dependencias

---

## 3) Run / Ejecutar

**Linux / macOS / Git Bash**
```bash
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=lab"
```

**Windows PowerShell**
```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=lab"
```

Or build and run JAR:  
O construir y ejecutar JAR:

```bash
mvn -DskipTests clean package
java -jar target/econexion-1.0-SNAPSHOT.jar --spring.profiles.active=lab
```

The app runs at **http://localhost:8080**.  
La app corre en **http://localhost:8080**.

---

## 4) Endpoints (Users) / Endpoints (Usuarios)

Base path: `/lab/users`

| Method | Path              | Description (EN)     | Descripción (ES)          |
|--------|-------------------|----------------------|---------------------------|
| GET    | `/lab/users`      | List all users       | Lista todos los usuarios  |
| GET    | `/lab/users/{id}` | Get user by id       | Obtener usuario por id    |
| POST   | `/lab/users`      | Create user          | Crear usuario             |
| PUT    | `/lab/users/{id}` | Update user          | Actualizar usuario        |
| DELETE | `/lab/users/{id}` | Delete user          | Eliminar usuario          |

**POST JSON** / **Ejemplo POST**
```json
{ "name": "Ada Lovelace", "email": "ada@demo.test" }
```

---

## 5) Endpoints (Weather) / Endpoints (Clima)

Base path: `/v1/weather`

| Method | Path                  | Description (EN)                | Descripción (ES)              |
|--------|-----------------------|---------------------------------|-------------------------------|
| GET    | `/v1/weather/{city}`  | Get stored weather for a city   | Consultar clima de una ciudad |
| POST   | `/v1/weather/{city}`  | Save weather (requires JWT)     | Guardar clima (requiere JWT)  |

**POST JSON**
```json
{ "weather": { "temp": 17.5, "pressure": 994.7, "humidity": 61 } }
```

---

## 6) Auth / Autenticación

**Login:**  
`POST /api/auth/login`  
Body:
```json
{ "username": "ada", "password": "school" }
```
Response:
```json
{ "token": "<JWT_TOKEN>" }
```

Use the token in requests:  
Usar el token en peticiones:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## 7) Config / Configuración

`application-lab.yml` highlights:  
```yaml
server:
  port: 8080

spring:
  main:
    allow-bean-definition-overriding: true

jwt:
  secret: ${JWT_SECRET:super-secreto-cambiame-por-env-32chars-min}
  expiration-minutes: 120
```

---

## 8) Quick cURL

**Login**
```bash
curl -X POST http://localhost:8080/api/auth/login   -H "Content-Type: application/json"   -d '{"username":"ada","password":"school"}'
```

**Weather POST (with token)**
```bash
curl -X POST http://localhost:8080/v1/weather/BOG   -H "Authorization: Bearer <TOKEN>"   -H "Content-Type: application/json"   -d '{"weather":{"temp":17.5,"pressure":994.71,"humidity":61}}'
```

---

## 9) Project Layout / Estructura del proyecto

```
src/main/java/io/econexion/
  ├─ lab/users/...
  ├─ weather/...
  ├─ security/
  │    ├─ SecurityConfig.java
  │    ├─ JwtUtil.java
  │    └─ ...
  └─ EconexionLabApplication.java
```

---

## 10) Notes / Notas
- Users CRUD works only in `lab` profile.  
- Weather POST requires JWT.  
- Login endpoint issues JWT using in‑memory user (`ada` / `school`).

---

## 11) License / Licencia
MIT (or your repo license).

— Updated 2025‑09‑14
