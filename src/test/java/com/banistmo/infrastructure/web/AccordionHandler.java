package com.banistmo.infrastructure.web;

import com.banistmo.domain.constants.Messages;
import com.banistmo.domain.constants.Scripts;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

public final class AccordionHandler {

    private AccordionHandler() {}

    public static Performable expandByLabel(String label) {
        return Task.where(Messages.EXPAND_SECTION + label,
                actor -> {
                    var driver = BrowseTheWeb.as(actor).getDriver();
                    var js = (JavascriptExecutor) driver;

                    Object result = js.executeScript(Scripts.findVisibleAndClick(label));

                    if (result != null && ((String) result).startsWith(Messages.NO_VISIBLE_ELEMENT)) {
                        js.executeScript(Scripts.findAllAndClick(label));
                        js.executeScript(Scripts.findAccordionAndClick(label));
                    }
                }
        );
    }
}
