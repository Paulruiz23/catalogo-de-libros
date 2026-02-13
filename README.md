# 📚Catálogo de Libros

[![Java](https://img.shields.io/badge/Java-17+-blue?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue?logo=postgresql)](https://www.postgresql.org/)
![Estado del proyecto](https://img.shields.io/badge/estado-completado-success)

---

## 🚀 Descripción

Este proyecto es un **catálogo de libros** desarrollado en Java con Spring Boot, que permite buscar libros mediante la API [Gutendex](https://gutendex.com/), almacenarlos en una base de datos PostgreSQL y realizar consultas avanzadas sobre libros y autores.

Está diseñado siguiendo buenas prácticas de desarrollo con Spring Data JPA, arquitectura en capas, y manejo eficiente de datos con Hibernate.

---

## 🛠 Funcionalidades

* Búsqueda de libros por título desde API externa
* Almacenamiento en base de datos PostgreSQL
* Listado de libros y autores guardados
* Filtrado de libros por idioma
* Búsqueda de autores por nombre
* Consulta de autores vivos en un año específico
* Estadísticas de descargas con Java Streams
* Prevención de duplicados automática

---

## 📁 Estructura del proyecto

<details>
<summary><strong>Click para expandir</strong></summary>

<br>

```text
catalogo-de-libros/
│
├─ src/
│   ├─ main/
│   │   ├─ java/com/alura/catalogodelibros/
│   │   │   ├─ model/
│   │   │   │   ├─ Autor.java              # Entidad JPA de autor
│   │   │   │   ├─ Libro.java              # Entidad JPA de libro
│   │   │   │   ├─ DatosAutor.java         # DTO para mapeo JSON
│   │   │   │   ├─ DatosLibro.java         # DTO para mapeo JSON
│   │   │   │   ├─ DatosRespuesta.java     # DTO respuesta API
│   │   │   │   └─ LibroEstadisticas.java  # Clase para estadísticas
│   │   │   ├─ repository/
│   │   │   │   ├─ AutorRepository.java    # Repositorio JPA de autores
│   │   │   │   └─ LibroRepository.java    # Repositorio JPA de libros
│   │   │   ├─ service/
│   │   │   │   ├─ ConsumoAPI.java         # Cliente HTTP para API
│   │   │   │   ├─ ConvierteDatos.java     # Conversor JSON con Jackson
│   │   │   │   └─ IConvierteDatos.java    # Interfaz del conversor
│   │   │   ├─ principal/
│   │   │   │   └─ Principal.java          # Menú y lógica principal
│   │   │   └─ CatlibrosApplication.java   # Clase principal Spring Boot
│   │   └─ resources/
│   │       └─ application.properties      # Configuración de BD
│
├─ pom.xml
└─ README.md
```
</details> 

---

## 🖥 Ejemplo de ejecución

<details>
<summary><strong>Click para ver</strong></summary>

<br>

```text
1 - Buscar libro por título
2 - Listar libros guardados
3 - Listar autores guardados
4 - Listar libros por idioma
5 - Listar Autores Vivos
6 - Estadísticas de libros
7 - Buscar autor por nombre
0 - Salir

Opción: 1

Escribe el nombre del libro:
> Don Quijote

Libro guardado correctamente.

======================================
Título    : Don Quijote de la Mancha
Autor     : Miguel de Cervantes Saavedra
Descargas : 15234
Idiomas   : es
======================================

Opción: 7

Escribe el nombre del autor que deseas buscar:
> Shakespeare

=== Autores encontrados: 1 ===

--------------------------------------
Nombre       : William Shakespeare
Nacimiento   : 1564
Fallecimiento: 1616
Libros       : Hamlet, Romeo and Juliet
--------------------------------------
```
</details> 

---

## ✅ Buenas prácticas aplicadas

* Arquitectura en capas (Model-Repository-Service-Controller)
* Inyección de dependencias con Spring
* DTOs con Records de Java 17
* Uso de Jackson para mapeo JSON (`@JsonAlias`, `@JsonIgnoreProperties`)
* Consultas optimizadas con JOIN FETCH (evita N+1 problem)
* Derived Queries de Spring Data JPA
* Validación de datos y manejo de excepciones
* Prevención de duplicados antes de insertar
* Streams de Java para estadísticas

---

## 🔧 Tecnologías utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 17+ | Lenguaje principal |
| Spring Boot | 3.x | Framework backend |
| Spring Data JPA | 3.x | Persistencia de datos |
| Hibernate | 6.x | ORM |
| PostgreSQL | 14+ | Base de datos |
| Jackson | 2.16 | Procesamiento JSON |
| Maven | 3.8+ | Gestión de dependencias |

---

## 🎓 Aprendizajes clave

Durante este proyecto aprendí:

* **Consumo de APIs REST** con HttpClient de Java
* **Persistencia de datos** con JPA/Hibernate
* **Relaciones entre entidades** (OneToMany, ManyToOne)
* **Consultas JPQL** personalizadas
* **Mapeo JSON** con anotaciones de Jackson
* **Optimización de consultas** con JOIN FETCH
* **Streams de Java** para procesamiento de datos
* **Spring Boot** y su ecosistema

---

## 📚 API utilizada

**Gutendex API** - Base de datos de libros del Proyecto Gutenberg
- URL: `https://gutendex.com/books/`
- Documentación: [gutendex.com](https://gutendex.com/)


---

## 👤 Autor

**Paul Stuart Ruiz Cabrera**

[![GitHub](https://img.shields.io/badge/GitHub-000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Paulruiz23)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/paulruiz4227/)



