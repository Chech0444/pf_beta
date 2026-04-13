package com.logistica.model;

public class SeguroDecorator extends EntradaDecorator {
    private double costoSeguro;

    public SeguroDecorator(AbstractEntrada entradaDecorada, double costoSeguro) {
        super(entradaDecorada);
        this.costoSeguro = costoSeguro;
    }

    @Override
    public double getPrecioFinal() {
        return super.getPrecioFinal() + costoSeguro;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " [+Seguro Cancelación]";
    }
}
