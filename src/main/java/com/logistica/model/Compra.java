package com.logistica.model;

import com.logistica.model.state.CompraCreada;
import com.logistica.model.state.IEstadoCompra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String idCompra;
    private Usuario usuarioAsociado;
    private Evento eventoAsociado;
    private LocalDateTime fechaCreacion;
    private double total;
    private IEstadoCompra estadoActual;
    private List<Entrada> itemsCompra;

    public Compra(String idCompra, Usuario usuarioAsociado, Evento eventoAsociado) {
        this.idCompra = idCompra;
        this.usuarioAsociado = usuarioAsociado;
        this.eventoAsociado = eventoAsociado;
        this.fechaCreacion = LocalDateTime.now();
        this.estadoActual = new CompraCreada();
        this.itemsCompra = new ArrayList<>();
        this.total = 0;
    }

    public void calcularTotal() {
        this.total = itemsCompra.stream().mapToDouble(Entrada::getPrecioFinal).sum();
    }

    public void pagar() {
        estadoActual.siguiente(this);
    }

    public void cancelar() {
        estadoActual.cancelar(this);
    }

    public void consultarDetalle() {
        System.out.println("=== Detalle Compra " + idCompra + " ===");
        System.out.println("Cliente: " + usuarioAsociado.getNombreCompleto());
        System.out.println("Evento: " + eventoAsociado.getNombre());
        System.out.println("Estado: " + estadoActual.getNombreEstado());
        System.out.println("Total: $" + total);
        System.out.println("Entradas: " + itemsCompra.size());
    }

    public void agregarEntrada(Entrada e) {
        itemsCompra.add(e);
        calcularTotal();
    }

    // Getters / Setters
    public String getIdCompra() { return idCompra; }
    public Usuario getUsuarioAsociado() { return usuarioAsociado; }
    public Evento getEventoAsociado() { return eventoAsociado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public double getTotal() { return total; }
    public IEstadoCompra getEstadoActual() { return estadoActual; }
    public void setEstado(IEstadoCompra estado) { this.estadoActual = estado; }
    public List<Entrada> getItemsCompra() { return itemsCompra; }

    @Override
    public String toString() {
        return "Compra " + idCompra + " | " + eventoAsociado.getNombre() + " | $" + total + " [" + estadoActual.getNombreEstado() + "]";
    }
}
