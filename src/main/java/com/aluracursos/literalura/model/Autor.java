package com.aluracursos.literalura.model;


import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name="Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private int anioDeNacimiento;
    private int anioDeMuerte;
//    @Transient
//    private List<Libro> libros;
    public Autor(){

    }

    public Autor(String nombre, int anioDeNacimiento, int anioDeMuerte) {
        this.nombre=nombre;
        this.anioDeNacimiento= anioDeNacimiento;
        this.anioDeMuerte=anioDeMuerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(int anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public int getAnioDeMuerte() {
        return anioDeMuerte;
    }

    public void setAnioDeMuerte(int anioDeMuerte) {
        this.anioDeMuerte = anioDeMuerte;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", anioDeNacimiento=" + anioDeNacimiento +
                ", anioDeMuerte=" + anioDeMuerte +
                '}';
    }
}

