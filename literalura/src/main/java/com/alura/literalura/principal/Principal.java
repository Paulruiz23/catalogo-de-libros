package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraMenu() {
        Scanner teclado = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("""
                    
                    1 - Buscar libro por título
                    2 - Listar libros guardados
                    3 - Listar autores
                    0 - Salir
                    """);

            String linea = teclado.nextLine();
            try {
                opcion = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                opcion = -1; // opción inválida si no es un número
            }

            switch (opcion) {
                case 1 -> buscarLibro(teclado);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 0 -> System.out.println("Cerrando aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    // 🔎 Buscar libro en API y guardar en BD evitando duplicados
    private void buscarLibro(Scanner teclado) {
        System.out.println("Escribe el nombre del libro:");
        String titulo = teclado.nextLine();

        String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");

        try {
            ConsumoAPI consumo = new ConsumoAPI();
            String json = consumo.obtenerDatos(url);

            ObjectMapper mapper = new ObjectMapper();
            DatosRespuesta datos = mapper.readValue(json, DatosRespuesta.class);

            if (datos.results().isEmpty()) {
                System.out.println("No se encontraron resultados.");
                return;
            }

            DatosLibro datosLibro = datos.results().get(0);

            // ✅ Buscar autor o crearlo
            DatosAutor dtoAutor = datosLibro.authors().get(0);
            Autor autor = autorRepository.findByNombre(dtoAutor.name())
                    .orElseGet(() -> autorRepository.save(new Autor(dtoAutor)));

            // ✅ Verificar si el libro ya existe
            Optional<Libro> libroExistente = libroRepository.findFirstByTituloIgnoreCaseAndAutor(datosLibro.title(), autor);

            if (libroExistente.isPresent()) {
                System.out.println("El libro ya existe en la base de datos.");
                return;
            }

            // ✅ Crear y guardar libro
            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);
            libroRepository.save(libro);

            System.out.println("Libro guardado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
    }

    // 📚 Listar libros guardados
    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(libro ->
                System.out.println("Libro: " + libro.getTitulo() +
                        " | Descargas: " + libro.getDescargas() +
                        " | Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "N/A"))
        );
    }

    // 👤 Listar autores guardados
    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(autor ->
                System.out.println("Autor: " + autor.getNombre() +
                        " | Nacimiento: " + autor.getNacimiento() +
                        " | Fallecimiento: " + autor.getFallecimiento())
        );
    }
}
