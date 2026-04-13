package com.logistica.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Patrón: Singleton
 * Propósito: Proveer un punto de acceso único y global a los datos en memoria,
 * actuando como nuestra base de datos inicializada.
 */
public class DataStore {
    private static DataStore instance;

    private List<Usuario> usuarios;
    private List<Evento> eventos;
    private List<Recinto> recintos;
    private List<Compra> compras;
    private List<Incidencia> incidencias;

    private DataStore() {
        this.usuarios = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.recintos = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.incidencias = new ArrayList<>();
        inicializarDatosDePrueba();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void inicializarDatosDePrueba() {
        // Usuario Admin
        Usuario admin = new Usuario("U001", "Admin General", "admin@logistica.com", "3001234567");
        usuarios.add(admin);

        // Usuario Cliente
        Usuario user = new Usuario("U002", "Juan Perez", "juan@test.com", "3019876543");
        user.addMetodoDePago("Visa **** 1234");
        usuarios.add(user);

        // Recinto y Zonas
        Recinto estadio = new Recinto("R001", "Estadio Principal", "Calle 123", "Bogotá");
        Zona general = new Zona("Z001", "General", 500, 100000.0);
        Zona vip = new Zona("Z002", "VIP", 50, 300000.0);
        
        // Agregar asientos a VIP como ejemplo
        vip.addAsiento(new Asiento("A1", "A", 1));
        vip.addAsiento(new Asiento("A2", "A", 2));

        estadio.addZona(general);
        estadio.addZona(vip);
        recintos.add(estadio);

        // Evento
        Evento concierto = new Evento("E001", "Concierto Rock", "Música", "Gran concierto de cierre", LocalDateTime.now().plusDays(30), estadio);
        concierto.setEstado(EstadoEvento.PUBLICADO);
        eventos.add(concierto);
    }

    // Getters para las listas
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Evento> getEventos() { return eventos; }
    public List<Recinto> getRecintos() { return recintos; }
    public List<Compra> getCompras() { return compras; }
    public List<Incidencia> getIncidencias() { return incidencias; }
}
