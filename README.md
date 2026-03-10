# Literalura - Challenge Java & Spring Boot (ONE)

## 📝 Descripción
Este proyecto es un buscador de libros interactivo que consume la API de **Gutendex** para obtener información literaria. Permite al usuario buscar libros, persistirlos en una base de datos PostgreSQL, y realizar diversos filtros y estadísticas directamente desde la consola.

Desarrollado como parte del programa **Oracle Next Education (ONE)** en conjunto con **Alura Latam**.

---

## 🚀 Funcionalidades
El sistema ofrece un menú interactivo con las siguientes opciones:
- **Buscar libro por título:** Obtiene datos de la API y los guarda automáticamente en la base de datos.
- **Listar libros registrados:** Muestra todos los libros almacenados localmente.
- **Listar autores registrados:** Muestra la lista de autores con sus años de vida.
- **Listar autores vivos en un año determinado:** Filtra autores según el año ingresado (incluye manejo de errores para entradas inválidas).
- **Estadísticas por idioma:** Muestra la cantidad de libros registrados según el idioma seleccionado (en, es, fr, pt).

---

## 🛠️ Tecnologías Utilizadas
- **Java 17** (o la versión que estés usando)
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL** (Base de datos relacional)
- **Jackson** (Para el mapeo de JSON)
- **Gutendex API**

---

## 📦 Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone [URL_DE_TU_REPOSITORIO]