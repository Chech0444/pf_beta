package com.logistica.model;

import java.time.LocalDateTime;

public class Incidencia {
    private String idIncidencia;
    private String tipo;
    private String descripcion;
    private LocalDateTime fecha;
    private String entidadAfectada;

    public Incidencia(String idIncidencia, String tipo, String descripcion, String entidadAfectada) {
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
        this.entidadAfectada = entidadAfectada;
    }

    public void registrar() {
        System.out.println("[INCIDENCIA] " + tipo + ": " + descripcion + " | Entidad: " + entidadAfectada);
    }

    public String getIdIncidencia() { return idIncidencia; }
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFecha() { return fecha; }
    public String getEntidadAfectada() { return entidadAfectada; }
}
