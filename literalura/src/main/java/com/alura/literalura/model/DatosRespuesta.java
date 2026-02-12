package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

// Ignora propiedades del JSON que no usamos
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuesta(

        // Total de resultados encontrados
        Integer count,

        // Lista de libros obtenidos
        List<DatosLibro> results

) {}
