package com.logistica.model.proxy;

import com.logistica.model.Filtros;
import com.logistica.model.Usuario;

/**
 * Patrón Proxy: Verifica permisos de admin antes de delegar al servicio real.
 */
public class ReporteProxy implements IReporteService {
    private ReporteReal service;
    private Usuario usuarioActual;

    public ReporteProxy(Usuario usuarioActual) {
        this.service = new ReporteReal();
        this.usuarioActual = usuarioActual;
    }

    private boolean esAdmin() {
        return usuarioActual != null && usuarioActual.isEsAdmin();
    }

    @Override
    public void generarReporteCSV(Filtros f, String filePath) {
        if (!esAdmin()) {
            System.out.println("[PROXY] Acceso denegado: solo administradores pueden generar reportes.");
            return;
        }
        service.generarReporteCSV(f, filePath);
    }

    @Override
    public void generarReportePDF(Filtros f, String filePath) {
        if (!esAdmin()) {
            System.out.println("[PROXY] Acceso denegado: solo administradores pueden generar reportes.");
            return;
        }
        service.generarReportePDF(f, filePath);
    }
}
