package com.logistica.controller;

import com.logistica.model.*;
import com.logistica.model.strategy.PagoStrategy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Patrón: Facade
 * Propósito: Proveer una interfaz simplificada al viewController para interactuar
 * con todos los subsistemas (Usuarios, Eventos, Compras).
 */
public class SistemaFacade {
    private DataStore db;

    public SistemaFacade() {
        this.db = DataStore.getInstance();
    }

    public Usuario login(String correo) {
        return db.getUsuarios().stream()
                 .filter(u -> u.getCorreoElectronico().equals(correo))
                 .findFirst().orElse(null);
    }

    public List<Evento> listarEventosDisponibles() {
        return db.getEventos().stream()
                 .filter(e -> e.getEstado() == EstadoEvento.PUBLICADO)
                 .collect(Collectors.toList());
    }

    public boolean procesarCompra(Compra compra, PagoStrategy estrategiaPago) {
        if (estrategiaPago.procesarPago(compra.getTotal())) {
            compra.setEstado(EstadoCompra.PAGADA);
            db.getCompras().add(compra);
            
            // Opcional: Generar incidencia de venta exitosa
            db.getIncidencias().add(new Incidencia("INC" + System.currentTimeMillis(),
                    "Venta Exitosa", "Se vendieron entradas por " + compra.getTotal(),
                    compra.getIdCompra()));

            return true;
        }
        return false;
    }
    
    // Métodos administrativos
    public void registrarUsuario(Usuario usuario) {
        db.getUsuarios().add(usuario);
    }
}
