package com.logistica.model.adapter;

/**
 * Patrón Adapter: Adapta distintos servicios de pago a una interfaz común.
 */
public interface IPagoAdapter {
    boolean procesarPago(double monto);
}
