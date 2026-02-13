package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    // Buscar autor por nombre para evitar duplicados
    Optional<Autor> findByNombre(String nombre);

    // JPQL: Lista autores vivos en un año determinado
    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.nacimiento <= :year AND (a.fallecimiento IS NULL OR a.fallecimiento >= :year)")
    List<Autor> findAutoresVivosEn(@Param("year") int year);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

}
