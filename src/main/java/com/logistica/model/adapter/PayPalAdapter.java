package com.logistica.model.adapter;

public class PayPalAdapter implements IPagoAdapter {
    @Override
    public boolean procesarPago(double monto) {
        System.out.println("[PayPal] Procesando pago de $" + monto + "... Éxito.");
        return true;
    }
}
