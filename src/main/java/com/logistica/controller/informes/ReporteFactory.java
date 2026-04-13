package com.logistica.controller.informes;

public class ReporteFactory {

    public static IReporteAdapter obtenerGenerador(String tipo) {
        if ("PDF".equalsIgnoreCase(tipo)) {
            return new PDFBoxAdapter();
        } else if ("CSV".equalsIgnoreCase(tipo)) {
            return new ApachePOIAdapter();
        }
        throw new IllegalArgumentException("Tipo de reporte no soportado: " + tipo);
    }
}
