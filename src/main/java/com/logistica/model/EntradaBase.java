package com.logistica.model;

public class EntradaBase extends AbstractEntrada {
    private double precioBase;

    public EntradaBase(String idEntrada, Zona zona, Asiento asiento, double precioBase) {
        super(idEntrada, zona, asiento);
        this.precioBase = precioBase;
    }

    @Override
    public double getPrecioFinal() {
        return this.precioBase;
    }

    @Override
    public String getDescripcion() {
        return "Entrada en Zona: " + (getZona() != null ? getZona().getNombre() : "N/A") + 
               (getAsiento() != null ? " - Asiento: " + getAsiento().getFila() + getAsiento().getNumero() : "");
    }
}
