package com.logistica.model.proxy;

import com.logistica.model.Compra;
import com.logistica.model.Filtros;
import com.logistica.controller.GestionEventos;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class ReporteReal implements IReporteService {

    @Override
    public void generarReporteCSV(Filtros f, String filePath) {
        List<Compra> compras = GestionEventos.getInstance().getCompras();
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("ID_Compra,Cliente,Evento,Total,Estado");
            for (Compra c : compras) {
                pw.println(c.getIdCompra() + "," +
                        c.getUsuarioAsociado().getNombreCompleto() + "," +
                        c.getEventoAsociado().getNombre() + "," +
                        c.getTotal() + "," +
                        c.getEstadoActual().getNombreEstado());
            }
            System.out.println("Reporte CSV generado en: " + filePath);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void generarReportePDF(Filtros f, String filePath) {
        List<Compra> compras = GestionEventos.getInstance().getCompras();
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                cs.newLineAtOffset(50, 750);
                cs.showText("Reporte de Compras - BookIt");
                cs.endText();
                cs.beginText();
                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
                cs.newLineAtOffset(50, 720);
                int y = 720;
                for (Compra c : compras) {
                    y -= 18;
                    if (y < 50) break;
                    cs.newLineAtOffset(0, -18);
                    cs.showText(c.getIdCompra() + " | " + c.getUsuarioAsociado().getNombreCompleto() + " | $" + c.getTotal() + " | " + c.getEstadoActual().getNombreEstado());
                }
                cs.endText();
            }
            doc.save(filePath);
            System.out.println("Reporte PDF generado en: " + filePath);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
