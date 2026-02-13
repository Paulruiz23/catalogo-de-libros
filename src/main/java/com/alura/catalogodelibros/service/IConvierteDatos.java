package com.alura.catalogodelibros.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
