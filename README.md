# Literalura

Este proyecto es una aplicación desarrollada en Java que permite buscar libros por título, listar libros y autores registrados, y realizar otras consultas relacionadas. Los datos se obtienen de una API externa y se almacenan en una base de datos.

# Menú

![image](https://github.com/AngelMendez1984/Literalura/assets/148590130/83854730-c290-4974-9e11-cdc43b0d97a4)

## Funcionamiento

- Elije una de las opciones presentadas en el menú y sigue las instrucciones, si es el primer uso la base de datos se encontrará vacía, es necesario realizar una busqueda de libro por titulo primero.

## Características

- Buscar libros por título utilizando una API externa.
- Listar libros y autores registrados en la base de datos.
- Listar autores vivos en un determinado año.
- Listar libros por idioma.
- Interfaz sencilla y amigable con el usuario.

## Base de datos
El proyecto necesita la creación de una base de datos, ademas de proporcionarle el usuario y contraseña de la misma, estos datos deben agregarse en el archivo application.properties como se muestra en la imagen siguiente

![image](https://github.com/AngelMendez1984/Literalura/assets/148590130/be21a62a-7b3b-422e-a4bd-bde6a73b397f)


## Tecnologías Utilizadas

- Java.
- Spring Boot.
- JPA (Java Persistence API).
- API externa (Gutenberg).

## Manejo de errores

- El programa maneja errores de entrada por parte del usuario, así como los provocados por respuestas incompletas de parte de la APi.
