package com.logistica.model;

public class Filtros {
    private String fecha;
    private String ciudad;
    private String categoria;
    private double precioMax;
    private String estado;

    public Filtros() {}

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecioMax() { return precioMax; }
    public void setPrecioMax(double precioMax) { this.precioMax = precioMax; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
