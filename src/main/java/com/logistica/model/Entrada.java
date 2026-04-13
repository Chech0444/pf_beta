package com.logistica.model;

/**
 * Patrón Decorator: Clase abstracta base para las entradas.
 */
public abstract class Entrada {
    protected String idEntrada;
    protected Zona zona;
    protected Asiento asiento;

    public Entrada(String idEntrada, Zona zona, Asiento asiento) {
        this.idEntrada = idEntrada;
        this.zona = zona;
        this.asiento = asiento;
    }

    public abstract double getPrecioFinal();

    public void generarComprobante() {
        System.out.println("=== COMPROBANTE ===");
        System.out.println("Entrada: " + idEntrada);
        System.out.println("Zona: " + (zona != null ? zona.getNombre() : "N/A"));
        System.out.println("Asiento: " + (asiento != null ? asiento.toString() : "General"));
        System.out.println("Precio Final: $" + getPrecioFinal());
        System.out.println("===================");
    }

    public void anular() {
        if (asiento != null) {
            asiento.cambiarEstado(EstadoAsiento.DISPONIBLE);
        }
        System.out.println("Entrada " + idEntrada + " anulada.");
    }

    public String getIdEntrada() { return idEntrada; }
    public Zona getZona() { return zona; }
    public Asiento getAsiento() { return asiento; }

    @Override
    public String toString() {
        return "Entrada " + idEntrada + " | " + (zona != null ? zona.getNombre() : "") + " | $" + getPrecioFinal();
    }
}
