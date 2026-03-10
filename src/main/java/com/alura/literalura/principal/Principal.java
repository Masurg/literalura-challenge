package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository; // <--- ESTA LÍNEA ES CLAVE
import com.alura.literalura.repository.LibroRepository; // <--- ESTA TAMBIÉN
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    // El constructor DEBE verse así exactamente
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    // ... el resto de tus métodos

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                1 - Buscar libro por título 
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un determinado año
                5 - Mostrar cantidad de Libros por Idioma
                0 - Salir
                """;
            System.out.println(menu);

            try {
                var linea = lectura.nextLine();
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Por favor, ingresa un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    mostrarCantidadLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void listarLibrosRegistrados() {
        // Cambiamos repository por libroRepository
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }
    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void mostrarCantidadLibrosPorIdioma() {
        System.out.println("""
            Ingrese el idioma para buscar los libros:
            es - español
            en - inglés
            fr - francés
            pt - portugués
            """);
        var idioma = lectura.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(idioma);

        // Usamos Streams para contar, tal como pide la tarjeta 10
        long cantidad = libros.stream()
                .count();

        if (cantidad > 0) {
            System.out.println("--- RESULTADO ---");
            System.out.println("Cantidad de libros en [" + idioma + "]: " + cantidad);
            System.out.println("Libros encontrados:");
            libros.forEach(l -> System.out.println("- " + l.getTitulo()));
        } else {
            System.out.println("No se encontraron libros en el idioma seleccionado.");
        }
    }
    private void listarAutoresVivosPorAnio() {
        System.out.println("Ingresa el año en el que deseas buscar autores vivos:");
        var entrada = lectura.nextLine();

        try {
            Integer anio = Integer.parseInt(entrada);
            List<Autor> autoresVivos = autorRepository.buscarAutoresVivosEnAnio(anio);

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos registrados en el año " + anio);
            } else {
                System.out.println("--- AUTORES VIVOS EN EL AÑO " + anio + " ---");
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            // Esta es la parte de "lidiar con valores inválidos" de la Tarjeta 11
            System.out.println("Error: Por favor ingresa un año válido en formato numérico.");
        }
    }

    private void buscarLibroWeb() {
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var nombreLibro = lectura.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        // Definimos la variable con minúscula: libroBuscado
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();


        if (libroBuscado.isPresent()) {
            DatosLibro datosLibro = libroBuscado.get();
            Libro libro = new Libro(datosLibro);

            // Usamos el nombre correcto del repositorio
            libroRepository.save(libro);

            System.out.println("--- LIBRO ENCONTRADO Y GUARDADO ---");
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }
}