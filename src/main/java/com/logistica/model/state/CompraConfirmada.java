package com.logistica.model.state;

import com.logistica.model.Compra;

public class CompraConfirmada implements IEstadoCompra {
    @Override
    public void siguiente(Compra c) {
        System.out.println("Compra " + c.getIdCompra() + " ya está confirmada. No hay siguiente estado.");
    }
    @Override
    public void cancelar(Compra c) {
        c.setEstado(new CompraReembolsada());
        System.out.println("Compra " + c.getIdCompra() + " -> REEMBOLSADA");
    }
    @Override
    public String getNombreEstado() { return "CONFIRMADA"; }
}
