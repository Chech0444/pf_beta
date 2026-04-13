package com.logistica.model.state;

import com.logistica.model.Compra;

/**
 * Patrón State: Interfaz para los estados de la Compra.
 */
public interface IEstadoCompra {
    void siguiente(Compra c);
    void cancelar(Compra c);
    String getNombreEstado();
}
