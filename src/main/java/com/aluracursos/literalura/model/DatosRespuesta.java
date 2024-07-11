package com.aluracursos.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosRespuesta(
        @JsonAlias ("count") int encontrados,
        @JsonAlias("results") List<DatosLibros> resultados
        ) {
}