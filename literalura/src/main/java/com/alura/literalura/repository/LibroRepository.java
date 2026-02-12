package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Libro findByTituloAndAutor(String titulo, Autor autor);

    // Devuelve todos los libros de un idioma específico
    List<Libro> findByIdioma(String idioma);

    // Devuelve la cantidad de libros de un idioma
    int countByIdioma(String idioma);
}
