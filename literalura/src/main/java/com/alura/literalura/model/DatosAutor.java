package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Ignora propiedades no usadas
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(

        // Nombre del autor
        String name,

        // JSON: birth_year → Java: birthYear
        @JsonAlias("birth_year")
        Integer birthYear,

        // JSON: death_year → Java: deathYear
        @JsonAlias("death_year")
        Integer deathYear

) {}

