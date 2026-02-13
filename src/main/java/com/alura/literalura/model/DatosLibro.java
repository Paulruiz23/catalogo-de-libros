package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// Ignora campos que no usamos del JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(

        Integer id,
        @JsonAlias("title")String title,
        List<DatosAutor> authors,
        List<String> languages,
        @JsonAlias("download_count") Integer downloadCount

) {}

