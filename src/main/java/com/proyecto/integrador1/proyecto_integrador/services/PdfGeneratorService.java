package com.proyecto.integrador1.proyecto_integrador.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.proyecto.integrador1.proyecto_integrador.entities.Ticket;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfGeneratorService {

    @Autowired
    private SpringTemplateEngine templateEngine; // usa el que Spring ya configuró

    public byte[] generatePdfFromTicket(Ticket ticket) throws IOException {
    Context context = new Context();
    context.setVariable("ticket", ticket);

    // Ruta absoluta o relativa del logo en tu proyecto
    String logoPath = "src/main/resources/static/IMG/logoHuntech.png";

    try {
        byte[] imageBytes = Files.readAllBytes(Paths.get(logoPath));
        String base64Logo = Base64.getEncoder().encodeToString(imageBytes);
        context.setVariable("logoBase64", base64Logo);
    } catch (IOException e) {
        // En caso de error, puedes dejar un string vacío
        context.setVariable("logoBase64", "");
    }

    String html = templateEngine.process("reporte-ticket", context); // sin .html

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    PdfRendererBuilder builder = new PdfRendererBuilder();
    builder.useFastMode();
    builder.withHtmlContent(html, null);
    builder.toStream(baos);
    builder.run();

    return baos.toByteArray();
}
}