package com.logistica.model.strategy;

import com.logistica.model.Zona;

public class TarifaPreventa implements ITarifaStrategy {
    @Override
    public double calcular(Zona zona) {
        return zona.getPrecioBase() * 0.8; // 20% descuento en preventa
    }
}
