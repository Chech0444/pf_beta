package com.logistica.model;

public class MetodoPago {
    private String tipo;
    private String detalle;

    public MetodoPago(String tipo, String detalle) {
        this.tipo = tipo;
        this.detalle = detalle;
    }

    public String getTipo() { return tipo; }
    public String getDetalle() { return detalle; }

    @Override
    public String toString() { return tipo + ": " + detalle; }
}
