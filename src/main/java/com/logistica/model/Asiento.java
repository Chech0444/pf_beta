package com.logistica.model;

public class Asiento {
    private String idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    public Asiento(String idAsiento, String fila, int numero) {
        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE; // Por defecto
    }

    public String getIdAsiento() { return idAsiento; }
    public String getFila() { return fila; }
    public int getNumero() { return numero; }
    
    public EstadoAsiento getEstado() { return estado; }
    public void setEstado(EstadoAsiento estado) { this.estado = estado; }
}
