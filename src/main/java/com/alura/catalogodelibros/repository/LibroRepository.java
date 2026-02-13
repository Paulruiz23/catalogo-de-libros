package com.alura.catalogodelibros.repository;

import com.alura.catalogodelibros.model.Autor;
import com.alura.catalogodelibros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Buscar libro por título y autor si hay autor
    Optional<Libro> findFirstByTituloIgnoreCaseAndAutor(String titulo, Autor autor);

    // Buscar libro por título solo (para libros sin autor)
    Optional<Libro> findFirstByTituloIgnoreCase(String titulo);

    // Devuelve todos los libros que contienen un idioma específico
    @Query("SELECT l FROM Libro l WHERE :idioma MEMBER OF l.idiomas")
    List<Libro> findByIdioma(@Param("idioma") String idioma);

}
