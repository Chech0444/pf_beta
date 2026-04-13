package com.logistica.model;

/**
 * Patrón Decorator: Clase abstracta base para las entradas.
 */
public abstract class Entrada {
    protected String idEntrada;
    protected Zona zona;
    protected Asiento asiento;
    protected EstadoEntrada estadoEntrada;

    public Entrada(String idEntrada, Zona zona, Asiento asiento) {
        this.idEntrada = idEntrada;
        this.zona = zona;
        this.asiento = asiento;
        this.estadoEntrada = EstadoEntrada.ACTIVA;
    }

    public abstract double getPrecioFinal();

    public void generarComprobante() {
        System.out.println("=== COMPROBANTE ===");
        System.out.println("Entrada: " + idEntrada);
        System.out.println("Zona: " + (zona != null ? zona.getNombre() : "N/A"));
        System.out.println("Asiento: " + (asiento != null ? asiento.toString() : "General"));
        System.out.println("Precio Final: $" + getPrecioFinal());
        System.out.println("Estado: " + estadoEntrada);
        System.out.println("===================");
    }

    public void anular() {
        this.estadoEntrada = EstadoEntrada.ANULADA;
        if (asiento != null) {
            asiento.cambiarEstado(EstadoAsiento.DISPONIBLE);
        }
        System.out.println("Entrada " + idEntrada + " anulada.");
    }

    public String getIdEntrada() { return idEntrada; }
    public Zona getZona() { return zona; }
    public Asiento getAsiento() { return asiento; }
    public EstadoEntrada getEstadoEntrada() { return estadoEntrada; }
    public void setEstadoEntrada(EstadoEntrada e) { this.estadoEntrada = e; }

    @Override
    public String toString() {
        return "Entrada " + idEntrada + " | " + (zona != null ? zona.getNombre() : "") + " | $" + String.format("%.0f", getPrecioFinal()) + " [" + estadoEntrada + "]";
    }
}
