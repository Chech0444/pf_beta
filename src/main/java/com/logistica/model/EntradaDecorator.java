package com.logistica.model;

public abstract class EntradaDecorator extends AbstractEntrada {
    protected AbstractEntrada entradaDecorada;

    public EntradaDecorator(AbstractEntrada entradaDecorada) {
        super(entradaDecorada.getIdEntrada(), entradaDecorada.getZona(), entradaDecorada.getAsiento());
        this.entradaDecorada = entradaDecorada;
    }

    @Override
    public double getPrecioFinal() {
        return entradaDecorada.getPrecioFinal();
    }

    @Override
    public String getDescripcion() {
        return entradaDecorada.getDescripcion();
    }
}
