package com.alura.catalogodelibros.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {}

    public Autor(DatosAutor datos) {
        this.nombre = datos.name();
        this.nacimiento = datos.birthYear();
        this.fallecimiento = datos.deathYear();
    }


    public List<Libro> getLibros() {
        return libros;
    }

    public String getNombre() {
        return nombre;
    }

    //Modifica TOSTRING
    @Override
    public String toString() {
        String librosStr = libros.isEmpty() ? "No tiene libros registrados" :
                libros.stream()
                        .map(Libro::getTitulo)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");

        return """
           --------------------------------------
           Nombre       : %s
           Nacimiento   : %s
           Fallecimiento: %s
           Libros       : %s
           --------------------------------------
           """.formatted(
                nombre,
                nacimiento != null ? nacimiento : "Desconocido",
                fallecimiento != null ? fallecimiento : "El autor se encuentra vivo",
                librosStr
        );
    }

}
