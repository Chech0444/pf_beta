package com.logistica.model.decorator;

import com.logistica.model.Entrada;

public class AccesoVipDecorator extends ServicioDecorator {
    public AccesoVipDecorator(Entrada e) { super(e); }

    @Override
    public double getCostoAdicional() { return 50000; }

    @Override
    public String toString() { return super.toString() + " [+VIP]"; }
}
