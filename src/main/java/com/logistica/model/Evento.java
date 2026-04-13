package com.logistica.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evento {
    private String idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String ciudad;
    private LocalDateTime fecha;
    private EstadoEvento estado;
    private Recinto recintoAsociado;
    private PoliticasEvento politicas;

    private List<Usuario> observadores = new ArrayList<>();

    public Evento(String idEvento, String nombre, String categoria, String descripcion,
                  String ciudad, LocalDateTime fecha, Recinto recinto, PoliticasEvento politicas) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.estado = EstadoEvento.BORRADOR;
        this.recintoAsociado = recinto;
        this.politicas = politicas;
    }

    // Observer: agregar/notificar observadores
    public void agregarObservador(Usuario u) {
        if (!observadores.contains(u)) observadores.add(u);
    }

    private void notificar(String mensaje) {
        for (Usuario u : observadores) {
            u.actualizar(mensaje);
        }
    }

    public void publicar() {
        this.estado = EstadoEvento.PUBLICADO;
        notificar("El evento '" + nombre + "' ha sido PUBLICADO.");
    }

    public void pausar() {
        this.estado = EstadoEvento.PAUSADO;
        notificar("El evento '" + nombre + "' ha sido PAUSADO.");
    }

    public void cancelar() {
        this.estado = EstadoEvento.CANCELADO;
        notificar("El evento '" + nombre + "' ha sido CANCELADO. Consulte políticas de reembolso.");
    }

    public Map<Zona, Integer> consultarDisponibilidad() {
        Map<Zona, Integer> disponibilidad = new HashMap<>();
        if (recintoAsociado != null) {
            for (Zona z : recintoAsociado.getZonas()) {
                long libres = z.getAsientos().stream()
                        .filter(a -> a.getEstado() == EstadoAsiento.DISPONIBLE).count();
                disponibilidad.put(z, (int) libres);
            }
        }
        return disponibilidad;
    }

    // Getters
    public String getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public void setNombre(String n) { this.nombre = n; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String c) { this.categoria = c; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String d) { this.descripcion = d; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String c) { this.ciudad = c; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime f) { this.fecha = f; }
    public EstadoEvento getEstado() { return estado; }
    public void setEstado(EstadoEvento e) { this.estado = e; }
    public Recinto getRecintoAsociado() { return recintoAsociado; }
    public void setRecintoAsociado(Recinto r) { this.recintoAsociado = r; }
    public PoliticasEvento getPoliticas() { return politicas; }

    @Override
    public String toString() { return nombre + " [" + estado + "] - " + ciudad; }
}
