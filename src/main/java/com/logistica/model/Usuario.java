package com.logistica.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Observer: Usuario actúa como observador de cambios en eventos.
 */
public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String password;
    private boolean esAdmin;
    private List<MetodoPago> metodosPago;
    private List<Compra> historialCompras;

    public Usuario(String idUsuario, String nombreCompleto, String email, String telefono, String password, boolean esAdmin) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.esAdmin = esAdmin;
        this.metodosPago = new ArrayList<>();
        this.historialCompras = new ArrayList<>();
    }

    // Observer: recibe notificaciones
    public void actualizar(String mensaje) {
        System.out.println("[NOTIFICACIÓN -> " + email + "] " + mensaje);
    }

    public void gestionarPerfil() {
        System.out.println("Gestionando perfil de " + nombreCompleto);
    }

    public void gestionarMetodosPago() {
        System.out.println("Métodos de pago de " + nombreCompleto + ": " + metodosPago);
    }

    public void consultarComprasAsociadas() {
        System.out.println("Compras de " + nombreCompleto + ": " + historialCompras.size());
    }

    // Getters y Setters
    public String getIdUsuario() { return idUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String n) { this.nombreCompleto = n; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String t) { this.telefono = t; }
    public String getPassword() { return password; }
    public boolean isEsAdmin() { return esAdmin; }
    public void setEsAdmin(boolean admin) { this.esAdmin = admin; }
    public List<MetodoPago> getMetodosPago() { return metodosPago; }
    public void addMetodoPago(MetodoPago m) { this.metodosPago.add(m); }
    public List<Compra> getHistorialCompras() { return historialCompras; }

    @Override
    public String toString() { return nombreCompleto + " (" + email + ")"; }
}
