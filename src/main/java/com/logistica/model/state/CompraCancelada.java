package com.logistica.model.state;

import com.logistica.model.Compra;

public class CompraCancelada implements IEstadoCompra {
    @Override
    public void siguiente(Compra c) {
        System.out.println("No se puede avanzar una compra cancelada.");
    }
    @Override
    public void cancelar(Compra c) {
        System.out.println("La compra ya está cancelada.");
    }
    @Override
    public String getNombreEstado() { return "CANCELADA"; }
}
