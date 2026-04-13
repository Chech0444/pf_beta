package com.logistica.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String idCompra;
    private Usuario usuario;
    private Evento evento;
    private LocalDateTime fechaCreacion;
    private EstadoCompra estado;
    private double total;
    private List<AbstractEntrada> entradas;

    private Compra(CompraBuilder builder) {
        this.idCompra = builder.idCompra;
        this.usuario = builder.usuario;
        this.evento = builder.evento;
        this.fechaCreacion = builder.fechaCreacion;
        this.estado = builder.estado;
        this.entradas = builder.entradas;
        calcularTotal();
    }

    public void calcularTotal() {
        this.total = this.entradas.stream().mapToDouble(AbstractEntrada::getPrecioFinal).sum();
    }

    public String getIdCompra() { return idCompra; }
    public Usuario getUsuario() { return usuario; }
    public Evento getEvento() { return evento; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public EstadoCompra getEstado() { return estado; }
    public double getTotal() { return total; }
    public List<AbstractEntrada> getEntradas() { return entradas; }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    // Patrón Builder
    public static class CompraBuilder {
        private String idCompra;
        private Usuario usuario;
        private Evento evento;
        private LocalDateTime fechaCreacion = LocalDateTime.now();
        private EstadoCompra estado = EstadoCompra.CREADA;
        private List<AbstractEntrada> entradas = new ArrayList<>();

        public CompraBuilder(String idCompra) {
            this.idCompra = idCompra;
        }

        public CompraBuilder usuario(Usuario user) {
            this.usuario = user;
            return this;
        }

        public CompraBuilder evento(Evento ev) {
            this.evento = ev;
            return this;
        }

        public CompraBuilder agregarEntrada(AbstractEntrada ent) {
            this.entradas.add(ent);
            return this;
        }

        public Compra build() {
            return new Compra(this);
        }
    }
}
