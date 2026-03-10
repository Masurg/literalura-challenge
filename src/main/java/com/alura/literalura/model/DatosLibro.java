package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Double numeroDeDescargas
) {
    // Agregamos esto para que al imprimir el libro se vea lindo:
    @Override
    public String toString() {
        return  "------ LIBRO ------\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + (autor.isEmpty() ? "Desconocido" : autor.get(0).nombre()) + "\n" +
                "Idioma: " + (idiomas.isEmpty() ? "Desconocido" : idiomas.get(0)) + "\n" +
                "Descargas: " + numeroDeDescargas + "\n" +
                "-------------------\n";
    }
}