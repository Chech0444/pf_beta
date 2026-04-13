package com.logistica.model.strategy;

public interface PagoStrategy {
    boolean procesarPago(double monto);
}
