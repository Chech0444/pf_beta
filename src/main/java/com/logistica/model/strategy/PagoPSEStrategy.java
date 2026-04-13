package com.logistica.model.strategy;

public class PagoPSEStrategy implements PagoStrategy {
    private String banco;

    public PagoPSEStrategy(String banco) {
        this.banco = banco;
    }

    @Override
    public boolean procesarPago(double monto) {
        System.out.println("Procesando pago de $" + monto + " mediante PSE - Banco " + banco);
        // Simulando que el pago es exitoso
        return true;
    }
}
