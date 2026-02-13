package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Integer descargas;
    //permite que JPA cree una tabla secundaria para almacenar los idiomas de cada libro
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;


    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datos) {
        this.titulo = datos.title();
        this.descargas = datos.downloadCount();

        // Guardar todos los idiomas del libro
        if (datos.languages() != null && !datos.languages().isEmpty()) {
            this.idiomas = new ArrayList<>(datos.languages());
        } else {
            this.idiomas = new ArrayList<>();
            this.idiomas.add("desconocido");
        }
    }




    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getDescargas() {
        return descargas;
    }


    //Modifica TOSTRING
    @Override
    public String toString() {
        return """
       ======================================
       Título    : %s
       Autor     : %s
       Descargas : %s
       Idiomas   : %s
       ======================================
       """.formatted(
                titulo != null ? titulo : "Desconocido",
                autor != null ? autor.getNombre() : "Desconocido",
                descargas != null ? descargas : 0,
                idiomas != null ? String.join(", ", idiomas) : "Desconocido"
        );
    }

}
