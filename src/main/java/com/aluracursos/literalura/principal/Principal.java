package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.DatosRespuesta;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;
    String json;

    public Principal(LibroRepository repositorioLibro, AutorRepository repositorioAutor) {
        this.repositorioLibro=repositorioLibro;
        this.repositorioAutor=repositorioAutor;
    }

    public void saludo(){
        var saludo= """
                *********************************************************************************
                * Bienvenid@ a Literalura, por favor selecciona una de las siguientes opciones: *
                *********************************************************************************
                """;
        System.out.println(saludo);
    }

    public void muestraElMenu() {


        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título.
                    2 - Listar libros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos en un determinado año.
                    5 - Listar libros por idioma.
                    0 - Salir
                    """;
            System.out.println(menu);

            try {
                String input = teclado.nextLine().trim(); // Obtener entrada del usuario
                opcion = Integer.parseInt(input); // Intentar convertir a entero

                switch (opcion) {
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        listarLibros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarPorIdioma();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingresa un número válido.");
                teclado.nextLine(); // Limpiar el buffer del teclado
            }
        }
    }
    public void guardaAutor(Autor autor) {
        // Verificar si el autor ya existe en la base de datos
        Optional<Autor> autorExistente = repositorioAutor.findByNombre(autor.getNombre());
        if (autorExistente.isPresent()) {
            System.out.println();
        } else {
            // Si no existe, guardar el nuevo autor
            repositorioAutor.save(autor);
        }
    }

private void buscarLibro() {
    System.out.println("Por favor escribe el título del libro que deseas buscar");
    var tituloLibro = teclado.nextLine();
    json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
    DatosRespuesta datos = conversor.obtenerDatos(json, DatosRespuesta.class);

    List<Libro> libros = datos.resultados().stream()
            .flatMap(d -> d.autores().stream().map(a -> new Libro(
                    d.titulo(),
                    a.nombre(),
                    String.join(", ", d.idiomas()),
                    d.numeroDeDescargas()
            )))
            //.limit(32)
            .collect(Collectors.toList());

    if (libros.isEmpty()) {
        System.out.println("No se encontraron libros con el título: " + "'" + tituloLibro +"'");
        return;
    }

    for (Libro libro : libros) {
        try {
            Optional<Libro> libroExistente = repositorioLibro.findByTitulo(libro.getTitulo());
            if (libroExistente.isPresent()) {
                System.out.println();
            } else {
                repositorioLibro.save(libro);
            }
        } catch (DataIntegrityViolationException e) {
            System.out.println();
        }
    }

        List<Autor> autores = datos.resultados().stream()
                .map(d -> {
                    if (d.autores().isEmpty()) {
                        return new Autor("Desconocido", 0, 0);
                    } else {
                        var a = d.autores().get(0);
                        return new Autor(a.nombre(), a.fechaDeNacimiento(), a.fechaDeMuerte());
                    }
                })
                .collect(Collectors.toList());
        autores.forEach(this::guardaAutor);

    libros.forEach(libro -> {
                        System.out.println("Titulo: " + libro.getTitulo());
                        System.out.println("Autor: " + libro.getAutor());
                        System.out.println("Idiomas: " + libro.getIdiomas());
                        System.out.println("Descargas: " + libro.getDescargas());
                        System.out.println("------------");
                    });
    }
    private void listarLibros() {
        System.out.println("Listando libros registrados...");
        List<Libro> librosEncontrados = repositorioLibro.findAll();
        librosEncontrados.forEach(libro -> {
            System.out.println("Titulo: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Idiomas: " + libro.getIdiomas());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("------------");
        });
    }
    private void listarAutores(){
        System.out.println("Listando autores registrados...");
        List<Autor> autoresEncontrados = repositorioAutor.findAll();
        autoresEncontrados.forEach(autor -> {
            System.out.println("Nombre: "+ autor.getNombre());
            System.out.println("Año de nacimiento: " + autor.getAnioDeNacimiento());
            System.out.println("Año de muerte: "+autor.getAnioDeMuerte());
            System.out.println("------------");
                });
    }
    public void listarAutoresVivos() {
        System.out.println("Por favor escribe el año en el que deseas buscar autores vivos:");
        int anio = teclado.nextInt();
        teclado.nextLine();  // Limpiar el buffer del teclado

        List<Autor> autoresVivos = repositorioAutor.findByAnioDeNacimientoLessThanEqualAndAnioDeMuerteGreaterThanEqual(anio, anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año: " + anio);
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            autoresVivos.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Año de nacimiento: " + autor.getAnioDeNacimiento());
                System.out.println("Año de muerte: " + (autor.getAnioDeMuerte() == 0 ? "Aún vivo" : autor.getAnioDeMuerte()));
                System.out.println("------------");
            });
        }
    }
    private void listarPorIdioma() {
        String mensaje = """
                Por favor escribe el idioma en el que deseas contar los libros:
                    *es -> Español.
                    *en -> Inglés.
                """;
        System.out.println(mensaje);
        String idioma = teclado.nextLine().trim().toLowerCase();

        long count = repositorioLibro.countByIdiomasContaining(idioma);

        System.out.println("Número de libros escritos en " + idioma + ": " + count);
    }
}