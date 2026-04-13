package com.logistica.model.state;

import com.logistica.model.Compra;

public class CompraPagada implements IEstadoCompra {
    @Override
    public void siguiente(Compra c) {
        c.setEstado(new CompraConfirmada());
        System.out.println("Compra " + c.getIdCompra() + " -> CONFIRMADA");
    }
    @Override
    public void cancelar(Compra c) {
        c.setEstado(new CompraReembolsada());
        System.out.println("Compra " + c.getIdCompra() + " -> REEMBOLSADA (pago devuelto)");
    }
    @Override
    public String getNombreEstado() { return "PAGADA"; }
}
