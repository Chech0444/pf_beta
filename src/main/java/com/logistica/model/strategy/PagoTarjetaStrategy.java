package com.logistica.model.strategy;

public class PagoTarjetaStrategy implements PagoStrategy {
    private String numeroTarjeta;

    public PagoTarjetaStrategy(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public boolean procesarPago(double monto) {
        System.out.println("Procesando pago de $" + monto + " con Tarjeta " + numeroTarjeta);
        // Simulando que el pago siempre es exitoso
        return true;
    }
}
