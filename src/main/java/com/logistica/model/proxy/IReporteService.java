package com.logistica.model.proxy;

import com.logistica.model.Filtros;

/**
 * Patrón Proxy: Interfaz para el servicio de reportes.
 */
public interface IReporteService {
    void generarReporteCSV(Filtros f, String filePath);
    void generarReportePDF(Filtros f, String filePath);
}
