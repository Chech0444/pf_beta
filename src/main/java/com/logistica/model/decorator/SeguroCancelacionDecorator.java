package com.logistica.model.decorator;

import com.logistica.model.Entrada;

public class SeguroCancelacionDecorator extends ServicioDecorator {
    public SeguroCancelacionDecorator(Entrada e) { super(e); }

    @Override
    public double getCostoAdicional() { return 15000; }

    @Override
    public String toString() { return super.toString() + " [+Seguro]"; }
}
