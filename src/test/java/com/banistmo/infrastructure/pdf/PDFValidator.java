package com.banistmo.infrastructure.pdf;

import com.banistmo.domain.constants.Labels;
import com.banistmo.domain.constants.Messages;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.InputStream;
import java.net.URL;

public final class PDFValidator {

    private final String pdfUrl;

    public PDFValidator(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public boolean isValidDocument() {
        try {
            try (InputStream is = new URL(pdfUrl).openStream();
                 PDDocument document = Loader.loadPDF(is.readAllBytes())) {

                String title = document.getDocumentInformation().getTitle();
                String author = document.getDocumentInformation().getAuthor();

                boolean hasReglamento = containsIgnoreCase(title, Labels.PDF_TITLE_MARKER);
                boolean isFromBancolombia = containsIgnoreCase(author, Labels.PDF_AUTHOR_MARKER);

                if (hasReglamento || isFromBancolombia) {
                    return true;
                }

                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                return containsIgnoreCase(text, Labels.PDF_VALIDATION_TEXT);
            }
        } catch (Exception e) {
            throw new RuntimeException(Messages.PDF_VALIDATION_ERROR + e.getMessage(), e);
        }
    }

    private boolean containsIgnoreCase(String value, String search) {
        return value != null && value.toLowerCase().contains(search.toLowerCase());
    }
}
