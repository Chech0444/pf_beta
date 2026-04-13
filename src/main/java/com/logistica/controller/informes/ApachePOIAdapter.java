package com.logistica.controller.informes;

import com.logistica.model.Compra;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ApachePOIAdapter implements IReporteAdapter {
    
    @Override
    public void generarReporteCompras(List<Compra> compras, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("ID_Compra,Cliente,Total,Estado");
            for(Compra c : compras) {
                pw.println(c.getIdCompra() + "," + 
                           c.getUsuario().getNombreCompleto() + "," + 
                           c.getTotal() + "," + 
                           c.getEstado().name());
            }
            System.out.println("Reporte CSV generado exitosamente en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
