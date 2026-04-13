package com.logistica.model.factory;

import com.logistica.model.*;

/**
 * Patrón Factory Method: Interfaz para crear distintos tipos de Entrada.
 */
public interface IEntradaFactory {
    Entrada crearEntrada(Zona z, Asiento a);
}
