package com.logistica.model;

import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private List<Asiento> asientos; // Si aplica numeración

    public Zona(String idZona, String nombre, int capacidad, double precioBase) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.asientos = new ArrayList<>();
    }

    public String getIdZona() { return idZona; }
    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public double getPrecioBase() { return precioBase; }

    public List<Asiento> getAsientos() { return asientos; }

    public void addAsiento(Asiento asiento) {
        this.asientos.add(asiento);
    }
}
