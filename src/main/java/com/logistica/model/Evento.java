package com.logistica.model;

import com.logistica.model.observer.IObserver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private LocalDateTime fechaHora;
    private EstadoEvento estado;
    private Recinto recinto;
    
    private transient List<IObserver> observadores = new ArrayList<>();

    public Evento(String idEvento, String nombre, String categoria, String descripcion, LocalDateTime fechaHora, Recinto recinto) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.recinto = recinto;
        this.estado = EstadoEvento.BORRADOR; // Estado por defecto
    }

    public void agregarObservador(IObserver obs) {
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    public String getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public EstadoEvento getEstado() { return estado; }
    public Recinto getRecinto() { return recinto; }

    public void setEstado(EstadoEvento estado) { 
        this.estado = estado; 
        if (this.estado == EstadoEvento.CANCELADO) {
            notificarCancelacion();
        }
    }

    private void notificarCancelacion() {
        for (IObserver obs : observadores) {
            obs.updateEventoCancelado(this);
        }
    }
}
