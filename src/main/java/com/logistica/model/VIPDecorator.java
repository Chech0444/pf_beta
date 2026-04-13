package com.logistica.model;

public class VIPDecorator extends EntradaDecorator {
    private double costoVIP;

    public VIPDecorator(AbstractEntrada entradaDecorada, double costoVIP) {
        super(entradaDecorada);
        this.costoVIP = costoVIP;
    }

    @Override
    public double getPrecioFinal() {
        return super.getPrecioFinal() + costoVIP;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " [+Acceso VIP]";
    }
}
