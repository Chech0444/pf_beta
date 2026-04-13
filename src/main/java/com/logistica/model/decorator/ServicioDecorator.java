package com.logistica.model.decorator;

import com.logistica.model.Entrada;
import com.logistica.model.Zona;
import com.logistica.model.Asiento;

/**
 * Patrón Decorator: Clase abstracta para servicios adicionales sobre Entrada.
 */
public abstract class ServicioDecorator extends Entrada {
    protected Entrada entradaDecorada;

    public ServicioDecorator(Entrada e) {
        super(e.getIdEntrada(), e.getZona(), e.getAsiento());
        this.entradaDecorada = e;
    }

    public abstract double getCostoAdicional();

    @Override
    public double getPrecioFinal() {
        return entradaDecorada.getPrecioFinal() + getCostoAdicional();
    }
}
