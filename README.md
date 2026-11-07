# CESDE Retos · Backend

API REST del Proyecto Integrador de CESDE. Conecta a las empresas con los estudiantes: las empresas publican retos, los estudiantes se postulan y CESDE administra todo el proceso.

Este backend es la pieza del medio. El front ([`proyecto_integrador_front`](../proyecto_integrador_front)) le pide todo en JSON y solo el backend lee y escribe en MySQL.

```
React + Vite (:5173)  ──JSON──▶  Spring Boot (:8080/api/v1)  ──JPA──▶  MySQL (:3307)
```

> **Guía interactiva:** abre [`docs/guia-backend.html`](docs/guia-backend.html) en el navegador para ver la arquitectura, el recorrido de cada petición, los endpoints, el modelo de datos y los roles explicados paso a paso.

## Tecnologías

- Java 17 y Spring Boot 4.1 (Web MVC, Data JPA, Validation)
- MySQL, con las tablas creadas por Hibernate (`ddl-auto=update`)
- `spring-security-crypto` solo para cifrar contraseñas con BCrypt

## Cómo ejecutarlo

1. Ten MySQL corriendo en el puerto **3307** y crea la base:
   ```sql
   CREATE DATABASE proyecto_integrador;
   ```
   El usuario es `root` sin contraseña. Si el tuyo es distinto, cámbialo en `src/main/resources/application.properties`.
2. Levanta el backend:
   ```bash
   ./mvnw spring-boot:run
   ```
   Queda en `http://localhost:8080/api/v1`.
3. En la carpeta del front ejecuta `npm install && npm run dev` y abre `http://localhost:5173`.

El backend solo acepta peticiones del navegador que vengan de `http://localhost:5173`, porque así está configurado el `@CrossOrigin` de cada controlador.

## Cómo está organizado

Cada petición pasa por cuatro capas, en este orden:

| Capa | Carpeta | Qué hace |
|------|---------|----------|
| Controller | `controller/` | Recibe la petición HTTP, valida con `@Valid` y responde |
| Service | `service/` | Aplica las reglas del negocio |
| Repository | `Repository/` | Lee y escribe en MySQL con `JpaRepository` |
| Model | `model/`, `dto/`, `enums/` | Entidades (una por tabla), el DTO del login y los enums |

## Endpoints

Todos van bajo `/api/v1`.

| Recurso | Endpoints |
|---------|-----------|
| Usuarios | `POST /usuario/registro` · `POST /usuario/login` · `GET /usuario/usuarios` · `DELETE /usuario/{id}` |
| Empresas | `POST /empresa` · `GET /empresa/empresas` · `GET /empresa/{id}` · `PUT /empresa/actualizar/{id}` · `DELETE /empresa/{id}` |
| Categorías | `POST /categoria` · `GET /categoria/categorias` · `GET /categoria/{id}` · `PUT /categoria/actualizar/{id}` · `DELETE /categoria/{id}` |
| Retos | `POST /reto` · `GET /reto?empresaId=` · `GET /reto/{id}` · `PUT /reto/{id}` · `DELETE /reto/{id}` |
| Postulaciones | `POST /registro` · `GET /registro/registros` · `GET /registro/{id}` · `PUT /registro/actualizar/{id}` · `DELETE /registro/{id}` |

## Roles

- **CESDE:** administra empresas, usuarios y categorías, ve todos los retos y gestiona las postulaciones.
- **EMPRESA:** su cuenta está ligada a una empresa. Ve y crea solo los retos de esa empresa (`GET /reto?empresaId=`).
- **ESTUDIANTE:** explora los retos, se postula y sigue el estado de sus postulaciones.

## Reglas importantes

- **Contraseñas:** se guardan cifradas con BCrypt y nunca salen en las respuestas (`WRITE_ONLY`).
- **Postulaciones:** un estudiante no puede postularse dos veces al mismo reto (409). La postulación pasa por `INSCRITO → EN_PROCESO → FINALIZADO` y guarda quién la aceptó, quién la finalizó y cuándo. Solo se puede cancelar mientras está en `INSCRITO`.
- **Borrado protegido:** no se borra en cascada. Una empresa con retos o usuarios, una categoría con retos, un reto con postulaciones o un usuario con postulaciones no se pueden borrar.

## Equipo

Jonathan Jurado Pineda · Juan Fernando Vásquez Usma · Brahian Pérez · Carlos Mario Vargas

