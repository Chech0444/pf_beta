package com.logistica.model;

import java.time.LocalDateTime;

/**
 * Patrón Builder: Construye objetos Evento paso a paso.
 */
public class EventoBuilder {
    private String idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String ciudad;
    private LocalDateTime fecha;
    private EstadoEvento estado = EstadoEvento.BORRADOR;
    private Recinto recinto;
    private PoliticasEvento politicas;

    public EventoBuilder conId(String id) { this.idEvento = id; return this; }
    public EventoBuilder conNombre(String n) { this.nombre = n; return this; }
    public EventoBuilder conCategoria(String cat) { this.categoria = cat; return this; }
    public EventoBuilder conDescripcion(String desc) { this.descripcion = desc; return this; }
    public EventoBuilder enCiudad(String c) { this.ciudad = c; return this; }
    public EventoBuilder enFecha(LocalDateTime f) { this.fecha = f; return this; }
    public EventoBuilder conEstado(EstadoEvento est) { this.estado = est; return this; }
    public EventoBuilder conRecinto(Recinto r) { this.recinto = r; return this; }
    public EventoBuilder conPoliticas(PoliticasEvento p) { this.politicas = p; return this; }

    public Evento build() {
        Evento e = new Evento(idEvento, nombre, categoria, descripcion, ciudad, fecha, recinto, politicas);
        e.setEstado(estado);
        return e;
    }
}
