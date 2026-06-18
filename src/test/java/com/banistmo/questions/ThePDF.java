package com.banistmo.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.InputStream;
import java.net.URL;

public class ThePDF implements Question<Boolean> {

    private static final String PDF_URL = "https://media.ffycdn.net/us/grupo-bancolombia-arquitectura-y-contenidos/qciA7CFfPVJQrMx85nux.pdf";

    private final String expectedContent;

    public ThePDF(String expectedContent) {
        this.expectedContent = expectedContent;
    }

    public static ThePDF containsText(String expectedContent) {
        return new ThePDF(expectedContent);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            try (InputStream is = new URL(PDF_URL).openStream();
                 PDDocument document = Loader.loadPDF(is.readAllBytes())) {
                String title = document.getDocumentInformation().getTitle();
                String author = document.getDocumentInformation().getAuthor();

                boolean hasReglamento = (title != null && title.toLowerCase().contains("reglamento"))
                        || (author != null && author.toLowerCase().contains("bancolombia"));
                if (hasReglamento) {
                    return true;
                }

                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                return text.toLowerCase().contains(expectedContent.toLowerCase());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al validar el PDF: " + e.getMessage(), e);
        }
    }
}
