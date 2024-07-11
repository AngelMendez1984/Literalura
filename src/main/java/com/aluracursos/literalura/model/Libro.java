package com.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name= "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String autor;
    private String idiomas;
    private Integer descargas;
    public Libro (){

    }

    public Libro(String titulo, String autor, String idiomas, Integer descargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.descargas = descargas;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }



    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", descargas=" + descargas ;
    }
}
