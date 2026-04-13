package com.logistica.model.adapter;

public class TarjetaCreditoAdapter implements IPagoAdapter {
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("[Tarjeta Crédito] Procesando pago de $" + monto + "... Éxito.");
        return true;
    }
}
