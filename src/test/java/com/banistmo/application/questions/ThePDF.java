package com.banistmo.application.questions;

import com.banistmo.domain.constants.Urls;
import com.banistmo.infrastructure.pdf.PDFValidator;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public final class ThePDF implements Question<Boolean> {

    private final PDFValidator validator;

    private ThePDF(PDFValidator validator) {
        this.validator = validator;
    }

    public static ThePDF isCorrect() {
        return new ThePDF(new PDFValidator(Urls.REGLAMENTO_PDF));
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return validator.isValidDocument();
    }
}
