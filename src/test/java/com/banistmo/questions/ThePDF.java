package com.banistmo.questions;

import com.banistmo.constants.Labels;
import com.banistmo.constants.Messages;
import com.banistmo.constants.Urls;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.InputStream;
import java.net.URL;

public final class ThePDF implements Question<Boolean> {

    private ThePDF() {}

    public static ThePDF isCorrect() {
        return new ThePDF();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            try (InputStream is = new URL(Urls.REGLAMENTO_PDF).openStream();
                 PDDocument document = Loader.loadPDF(is.readAllBytes())) {

                String title = document.getDocumentInformation().getTitle();
                String author = document.getDocumentInformation().getAuthor();

                if (containsIgnoreCase(title, Labels.PDF_TITLE_MARKER)
                        || containsIgnoreCase(author, Labels.PDF_AUTHOR_MARKER)) {
                    return true;
                }

                String text = new PDFTextStripper().getText(document);
                return containsIgnoreCase(text, Labels.PDF_TITLE_MARKER);
            }
        } catch (Exception e) {
            throw new RuntimeException(Messages.PDF_VALIDATION_ERROR + e.getMessage(), e);
        }
    }

    private boolean containsIgnoreCase(String value, String search) {
        return value != null && value.toLowerCase().contains(search.toLowerCase());
    }
}
