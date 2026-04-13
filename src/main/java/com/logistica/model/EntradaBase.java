package com.logistica.model;

public class EntradaBase extends Entrada {
    private double precioBase;

    public EntradaBase(String idEntrada, Zona zona, Asiento asiento, double precioBase) {
        super(idEntrada, zona, asiento);
        this.precioBase = precioBase;
    }

    @Override
    public double getPrecioFinal() {
        return precioBase;
    }
}
