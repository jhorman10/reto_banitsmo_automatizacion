package com.banistmo.application.tasks;

import com.banistmo.domain.constants.Labels;
import com.banistmo.domain.constants.Messages;
import com.banistmo.infrastructure.web.AccordionHandler;
import net.serenitybdd.screenplay.Performable;

public final class ExpandAccordion {

    private ExpandAccordion() {}

    public static Performable documentosSection() {
        return AccordionHandler.expandByLabel(Labels.DOCUMENTOS);
    }
}
