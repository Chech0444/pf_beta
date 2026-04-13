package com.logistica.controller.informes;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.logistica.model.Compra;
import java.io.IOException;
import java.util.List;

public class PDFBoxAdapter implements IReporteAdapter {

    @Override
    public void generarReporteCompras(List<Compra> compras, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Reporte de Compras - BookIt");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(50, 650);
                
                int yOffset = 650;
                for (Compra c : compras) {
                    yOffset -= 20;
                    if(yOffset < 50) break; // simple logic
                    
                    contentStream.newLineAtOffset(0, -20);
                    String linea = "Compra: " + c.getIdCompra() + " | Cliente: " + c.getUsuario().getNombreCompleto() + " | Total: $" + c.getTotal();
                    contentStream.showText(linea);
                }
                contentStream.endText();
            }
            document.save(filePath);
            System.out.println("Reporte PDF generado exitosamente en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
