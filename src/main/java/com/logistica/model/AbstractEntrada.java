package com.logistica.model;

public abstract class AbstractEntrada {
    protected String idEntrada;
    protected Zona zona;
    protected Asiento asiento;

    public AbstractEntrada(String idEntrada, Zona zona, Asiento asiento) {
        this.idEntrada = idEntrada;
        this.zona = zona;
        this.asiento = asiento;
    }

    public abstract double getPrecioFinal();
    public abstract String getDescripcion();

    public String getIdEntrada() { return idEntrada; }
    public Zona getZona() { return zona; }
    public Asiento getAsiento() { return asiento; }
}
