# Proyecto CRUD - Universidad

**Autor:** Esther Sara Copa Quispe  
**Email:** e.sara.cq.25@gmail.com

---

## 🚀 Descripción General

Este repositorio contiene la base de un sistema universitario desarrollado con **Spring Boot**. El objetivo es implementar operaciones CRUD para la gestión de datos universitarios, utilizando **PostgreSQL** como base de datos y **JWT** para autenticación. Cada grupo debe trabajar en su propia rama según las instrucciones del docente.

---

## 🎯 Objetivo

Completar las operaciones requeridas sobre el proyecto universitario, permitiendo a cada grupo trabajar en su propia rama según las instrucciones del docente.

---

## 🗂️ Estructura del Proyecto

```
RegistroUniversitario/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
├── pom.xml
└── README.md
```
---

## ⚙️ Configuración Principal

### Base de Datos

- **Tipo:** PostgreSQL  
- **URL:** `jdbc:postgresql://localhost:5432/universidad2`  
- **Usuario:** `postgres`  
- **Contraseña:** `contraseña`  
- **Driver:** `org.postgresql.Driver`

### JPA & Hibernate

- **Estrategia DDL:** `update`
- **Dialecto:** `org.hibernate.dialect.PostgreSQLDialect`
- **Mostrar SQL:** `true`

### Servidor

- **Puerto:** `8081`

### Seguridad

- **JWT Secret:** Definido en `application.properties` bajo `app.jwtSecret`
- **JWT Expiración:** 1 día (`86400000` ms)

### Sesiones

- **Tipo de almacenamiento:** JDBC
- **Timeout:** 30 minutos

---

## 🏗️ Arquitectura y Flujo del Proyecto

1. **Modelos (Model):**
   - Clases que representan las tablas principales de la base de datos:
     - `Materia`
     - `Docente`
     - `Estudiante`
     - `Inscripcion`
   - Cada clase modela los atributos y relaciones de la entidad correspondiente.

2. **DTO (Data Transfer Object):**
   - Objetos para transferir datos entre el cliente y el servidor, evitando exponer directamente las entidades del modelo.
   - Permiten controlar qué información se envía y recibe en cada operación.

3. **Validaciones:**
   - Reglas de validación sobre los DTOs o entidades para asegurar la integridad de los datos antes de ser procesados o almacenados.
   - Ejemplo: validación de campos obligatorios, formatos de correo, etc.

4. **Repositorio (Repository):**
   - Interfaces que extienden de `JpaRepository` o similares.
   - Permiten realizar operaciones CRUD sobre las entidades en la base de datos de manera sencilla y eficiente.

5. **Servicio (Service):**
   - Define la lógica de negocio de la aplicación.
   - Los servicios utilizan los repositorios para acceder a los datos y aplicar las reglas necesarias antes de devolver la información o realizar cambios.

6. **Implementación del Servicio (ImplService):**
   - Implementa las interfaces de servicio.
   - Aquí se concreta la lógica definida en el servicio, orquestando las operaciones entre los distintos repositorios y aplicando validaciones adicionales si es necesario.

7. **Controladores (Controller):**
   - Exponen los endpoints REST para interactuar con el sistema.
   - Reciben las solicitudes del cliente, llaman a los servicios y devuelven las respuestas adecuadas.

---

## 🛠️ Instalación y Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/LiaRos-ai/RegistroUniversitario.git
   ```
2. Configura la base de datos PostgreSQL según los parámetros en `src/main/resources/application.properties`.
3. Verifica que el puerto configurado sea el `8081` o cámbialo según tu necesidad.
4. Modifica el usuario, contraseña y nombre de la base de datos en `application.properties` si es necesario.
5. Verifica que la versión de Java en tu máquina coincida con la del archivo `pom.xml` usando:
   ```bash
   java --version
   ```
   Si coincide, no es necesario modificar el `pom.xml`.
6. Ejecuta el proyecto:
   ```bash
   ./mvnw spring-boot:run
   ```
7. Accede a la aplicación en [http://localhost:8081](http://localhost:8081).

---

## 📦 Cómo trabajar en tu rama

1. Clona el repositorio:
   ```bash
   git clone https://github.com/LiaRos-ai/RegistroUniversitario.git
   ```
2. Crea y cambia a tu rama de trabajo:
   ```bash
   git checkout -b nombre-de-tu-rama
   ```
3. Realiza tus cambios y súbelos a tu rama:
   ```bash
   git add .
   git commit -m "Descripción de tus cambios"
   git push origin nombre-de-tu-rama
   ```

---

## 📌 Notas Importantes

- Cambia la clave JWT por una segura en producción.
- Puedes habilitar Redis para cache y sesiones si lo necesitas (ver líneas comentadas en `application.properties`).
- Sigue las buenas prácticas de desarrollo y mantén tu rama actualizada con la rama principal del proyecto.

---

## 📚 Licencia

Proyecto educativo.