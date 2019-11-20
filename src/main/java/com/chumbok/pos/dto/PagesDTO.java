package com.chumbok.pos.dto;

public class PagesDTO {
    private String titulo;
    private int numeroDePagina;

    public PagesDTO(String titulo, int numeroDePagina) {
        this.titulo = titulo;
        this.numeroDePagina = numeroDePagina;
    }

    public PagesDTO() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroDePagina() {
        return numeroDePagina;
    }

    public void setNumeroDePagina(int numeroDePagina) {
        this.numeroDePagina = numeroDePagina;
    }
}
