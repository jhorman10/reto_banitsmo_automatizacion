package com.banistmo.tasks;

import com.banistmo.constants.Labels;
import com.banistmo.constants.Messages;
import com.banistmo.interactions.ClickAccordionByLabel;
import net.serenitybdd.screenplay.Performable;

public final class ExpandAccordion {

    private ExpandAccordion() {}

    public static Performable documentosSection() {
        return ClickAccordionByLabel.withText(Labels.DOCUMENTOS);
    }
}
