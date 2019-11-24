package com.chumbok.pos.dto;

public class PagesDTO {
    private String titulo;
    private int numeroDePagina;
    private String disabled;

    public PagesDTO(String titulo, int numeroDePagina) {
        this.titulo = titulo;
        this.numeroDePagina = numeroDePagina;
        this.disabled = "enabled";
    }

    public PagesDTO(String titulo, int numeroDePagina, String disabled) {
        this.titulo = titulo;
        this.numeroDePagina = numeroDePagina;
        this.disabled = disabled;
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

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }
}
