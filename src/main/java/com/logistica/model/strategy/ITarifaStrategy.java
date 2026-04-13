package com.logistica.model.strategy;

import com.logistica.model.Zona;

/**
 * Patrón Strategy: Define una familia de algoritmos de cálculo de tarifa.
 */
public interface ITarifaStrategy {
    double calcular(Zona zona);
}
