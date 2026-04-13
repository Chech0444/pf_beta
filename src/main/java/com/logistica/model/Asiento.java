package com.logistica.model;

public class Asiento {
    private String idAsiento;
    private String fila;
    private String numero;
    private EstadoAsiento estado;

    public Asiento(String idAsiento, String fila, String numero) {
        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }

    public String getIdAsiento() { return idAsiento; }
    public String getFila() { return fila; }
    public String getNumero() { return numero; }
    public EstadoAsiento getEstado() { return estado; }

    public void cambiarEstado(EstadoAsiento nuevo) {
        this.estado = nuevo;
    }

    @Override
    public String toString() { return "Fila " + fila + " - Asiento " + numero + " [" + estado + "]"; }
}
