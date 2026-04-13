package com.logistica.model;

import com.logistica.model.strategy.ITarifaStrategy;
import com.logistica.model.strategy.TarifaEstandar;
import java.util.ArrayList;
import java.util.List;

public class Zona {
    private String idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private ITarifaStrategy estrategiaTarifa;
    private List<Asiento> asientos;

    public Zona(String idZona, String nombre, int capacidad, double precioBase) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.estrategiaTarifa = new TarifaEstandar();
        this.asientos = new ArrayList<>();
    }

    public double consultarOcupacion() {
        if (asientos.isEmpty()) return 0;
        long vendidos = asientos.stream().filter(a -> a.getEstado() == EstadoAsiento.VENDIDO || a.getEstado() == EstadoAsiento.RESERVADO).count();
        return (double) vendidos / asientos.size() * 100;
    }

    public void definirPrecioBase(double precio) { this.precioBase = precio; }
    public void definirCapacidad(int cap) { this.capacidad = cap; }
    public double calcularPrecioFinal() { return estrategiaTarifa.calcular(this); }

    public String getIdZona() { return idZona; }
    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public double getPrecioBase() { return precioBase; }
    public ITarifaStrategy getEstrategiaTarifa() { return estrategiaTarifa; }
    public void setEstrategiaTarifa(ITarifaStrategy estrategia) { this.estrategiaTarifa = estrategia; }
    public List<Asiento> getAsientos() { return asientos; }
    public void addAsiento(Asiento a) { this.asientos.add(a); }

    @Override
    public String toString() { return nombre + " ($" + precioBase + ")"; }
}
