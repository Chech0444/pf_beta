package com.logistica.model.strategy;

import com.logistica.model.Zona;

public class TarifaEstandar implements ITarifaStrategy {
    @Override
    public double calcular(Zona zona) {
        return zona.getPrecioBase();
    }
}
