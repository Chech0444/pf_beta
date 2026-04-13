package com.logistica.model.decorator;

import com.logistica.model.Entrada;

public class MerchandisingDecorator extends ServicioDecorator {
    public MerchandisingDecorator(Entrada e) { super(e); }

    @Override
    public double getCostoAdicional() { return 25000; }

    @Override
    public String toString() { return super.toString() + " [+Merchandising]"; }
}
