package com.logistica.model.factory;

import com.logistica.model.*;

public class EntradaNumeradaFactory implements IEntradaFactory {
    @Override
    public Entrada crearEntrada(Zona z, Asiento a) {
        String id = "ENT-" + System.currentTimeMillis();
        if (a != null) {
            a.cambiarEstado(EstadoAsiento.RESERVADO);
        }
        return new EntradaBase(id, z, a, z.calcularPrecioFinal());
    }
}
