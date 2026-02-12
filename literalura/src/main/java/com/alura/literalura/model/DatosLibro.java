package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// Ignora campos que no usamos del JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(

        // ID del libro
        Integer id,

        // Título (JSON trae "title" igual)
        @JsonAlias("title")String title,

        // Lista de autores
        List<DatosAutor> authors,

        // Lista de idiomas
        List<String> languages,

        // JSON trae "download_count" → lo convertimos a camelCase
        @JsonAlias("download_count") Integer downloadCount

) {}

