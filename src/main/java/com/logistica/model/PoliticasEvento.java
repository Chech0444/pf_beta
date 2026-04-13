package com.logistica.model;

public class PoliticasEvento {
    private String politicaCancelacion;
    private String politicaReembolso;

    public PoliticasEvento(String politicaCancelacion, String politicaReembolso) {
        this.politicaCancelacion = politicaCancelacion;
        this.politicaReembolso = politicaReembolso;
    }

    public String getPoliticaCancelacion() { return politicaCancelacion; }
    public void setPoliticaCancelacion(String p) { this.politicaCancelacion = p; }
    public String getPoliticaReembolso() { return politicaReembolso; }
    public void setPoliticaReembolso(String p) { this.politicaReembolso = p; }
}
