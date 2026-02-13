package com.alura.catalogodelibros.principal;

import com.alura.catalogodelibros.model.*;
import com.alura.catalogodelibros.repository.AutorRepository;
import com.alura.catalogodelibros.repository.LibroRepository;
import com.alura.catalogodelibros.service.ConsumoAPI;
import com.alura.catalogodelibros.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private final ConvierteDatos conversor;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository, ConvierteDatos conversor) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.conversor = conversor;
    }

    public void muestraMenu() {
        Scanner teclado = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
        
        1 - Buscar libro por título
        2 - Listar libros guardados
        3 - Listar autores guardados
        4 - Listar libros por idioma
        5 - Listar Autores Vivos
        6 - Estadísticas de libros
        7 - Buscar autor por nombre
        0 - Salir
        """);

            String linea = teclado.nextLine();
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> buscarLibro(teclado);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarLibrosPorIdioma(teclado);
                case 5 -> listarAutoresVivos(teclado);
                case 6 -> mostrarEstadisticasLibros();
                case 7 -> buscarAutorPorNombre(teclado);
                case 0 -> System.out.println("Cerrando aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    // Buscar autor por nombre en la base de datos
    private void buscarAutorPorNombre(Scanner teclado) {
        System.out.println("Escribe el nombre del autor que deseas buscar:");
        String nombre = teclado.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        List<Autor> autoresEncontrados = autorRepository.findByNombreContainingIgnoreCase(nombre);

        if (autoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron autores con el nombre: " + nombre);
            return;
        }

        System.out.println("\n=== Autores encontrados: " + autoresEncontrados.size() + " ===\n");
        autoresEncontrados.forEach(System.out::println);
    }

    // Resto de métodos existentes...
    private void buscarLibro(Scanner teclado) {
        System.out.println("Escribe el nombre del libro:");
        String titulo = teclado.nextLine();

        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");

        try {
            ConsumoAPI consumo = new ConsumoAPI();
            String json = consumo.obtenerDatos(url);

            DatosRespuesta datos = conversor.obtenerDatos(json, DatosRespuesta.class);

            if (datos.results().isEmpty()) {
                System.out.println("No se encontraron resultados.");
                return;
            }

            DatosLibro datosLibro = datos.results().get(0);

            // Buscar o crear autor
            Autor autor = null;
            if (datosLibro.authors() != null && !datosLibro.authors().isEmpty()) {
                DatosAutor dtoAutor = datosLibro.authors().get(0);
                autor = autorRepository.findByNombre(dtoAutor.name())
                        .orElseGet(() -> autorRepository.save(new Autor(dtoAutor)));
            }

            // Verificar si el libro ya existe
            Optional<Libro> libroExistente;
            if (autor != null) {
                libroExistente = libroRepository.findFirstByTituloIgnoreCaseAndAutor(datosLibro.title(), autor);
            } else {
                libroExistente = libroRepository.findFirstByTituloIgnoreCase(datosLibro.title());
            }

            if (libroExistente.isPresent()) {
                System.out.println("El libro ya existe en la base de datos.");
                return;
            }

            // Crear libro y asignar autor
            Libro libro = new Libro(datosLibro);
            if (autor != null) libro.setAutor(autor);

            // Asegurar que los idiomas sean únicos
            if (libro.getIdiomas() != null) {
                libro.setIdiomas(new ArrayList<>(new HashSet<>(libro.getIdiomas())));
            }

            // Guardar libro
            libroRepository.save(libro);
            System.out.println("Libro guardado correctamente.");
            System.out.println(libro);

        } catch (Exception e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados.");
            return;
        }
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAllWithLibros();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
            return;
        }
        autores.forEach(System.out::println);
    }

    private void listarLibrosPorIdioma(Scanner teclado) {
        System.out.println("Escribe el idioma a buscar (ej: en, es, fr, pt):");
        String idioma = teclado.nextLine().trim();

        List<Libro> libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma: " + idioma);
            return;
        }

        libros.forEach(System.out::println);
    }

    private void listarAutoresVivos(Scanner teclado) {
        System.out.println("Ingresa el año para consultar autores vivos:");
        int year;

        try {
            year = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingresa un número válido.");
            return;
        }

        List<Autor> autoresVivos = autorRepository.findAutoresVivosEn(year);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año: " + year);
            return;
        }

        System.out.println("Autores vivos en " + year + ":");
        autoresVivos.forEach(System.out::println);
    }

    private void mostrarEstadisticasLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados para generar estadísticas.");
            return;
        }

        IntSummaryStatistics stats = libros.stream()
                .mapToInt(Libro::getDescargas)
                .summaryStatistics();

        System.out.println(new LibroEstadisticas(stats));
    }
}