package com.logistica.model;

import com.logistica.model.observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements IObserver {
    private String idUsuario;
    private String nombreCompleto;
    private String correoElectronico;
    private String numeroTelefono;
    private List<String> metodosDePago;

    public Usuario(String idUsuario, String nombreCompleto, String correoElectronico, String numeroTelefono) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.metodosDePago = new ArrayList<>();
    }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }

    public List<String> getMetodosDePago() { return metodosDePago; }
    
    public void addMetodoDePago(String metodo) {
        if (!this.metodosDePago.contains(metodo)) {
            this.metodosDePago.add(metodo);
        }
    }

    @Override
    public void updateEventoCancelado(Evento evento) {
        // En una app real esto enviaría un email. Aquí simulamos en consola.
        System.out.println("====== NOTIFICACIÓN ======");
        System.out.println("Para: " + this.correoElectronico);
        System.out.println("Mensaje: Lamentamos informale que el evento '" + evento.getNombre() + "' ha sido CANCELADO.");
        System.out.println("==========================");
    }
}
