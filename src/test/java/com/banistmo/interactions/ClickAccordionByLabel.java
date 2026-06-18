package com.banistmo.interactions;

import com.banistmo.constants.Messages;
import com.banistmo.constants.Scripts;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

public final class ClickAccordionByLabel {

    private ClickAccordionByLabel() {}

    public static Performable withText(String label) {
        return Task.where(Messages.EXPAND_SECTION + label,
                actor -> {
                    var js = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();

                    Object result = js.executeScript(Scripts.findVisibleAndClick(label));

                    if (result != null && ((String) result).startsWith(Messages.NO_VISIBLE_ELEMENT_FOUND)) {
                        js.executeScript(Scripts.findAllAndClick(label));
                        js.executeScript(Scripts.findAccordionAndClick(label));
                    }
                }
        );
    }
}
