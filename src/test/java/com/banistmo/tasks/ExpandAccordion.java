package com.banistmo.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;

public class ExpandAccordion {

    public static Performable documentosSection() {
        return Task.where("{0} expande la seccion Documentos",
                actor -> {
                    var driver = BrowseTheWeb.as(actor).getDriver();
                    var js = (JavascriptExecutor) driver;

                    // Find any visible element containing "Documentos" text and click it
                    Object result = js.executeScript(
                            "var els = document.querySelectorAll('*');" +
                            "for (var i = 0; i < els.length; i++) {" +
                            "  if (els[i].offsetParent !== null && els[i].textContent.trim() === 'Documentos') {" +
                            "    els[i].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));" +
                            "    return 'clicked: ' + els[i].tagName + '.' + (els[i].className || '');" +
                            "  }" +
                            "}" +
                            "return 'no visible element found';"
                    );

                    // If no visible element, try hidden ones
                    if (result != null && ((String) result).startsWith("no")) {
                        js.executeScript(
                                "var els = document.querySelectorAll('*');" +
                                "for (var i = 0; i < els.length; i++) {" +
                                "  if (els[i].textContent.trim() === 'Documentos') {" +
                                "    els[i].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));" +
                                "    return;" +
                                "  }" +
                                "}"
                        );
                        js.executeScript(
                                "var els = document.querySelectorAll('[class*=\"accordion\"], [class*=\"Accordion\"], .cbc-heading__title, .cbc-heading, [class*=\"heading\"]');" +
                                "for (var i = 0; i < els.length; i++) {" +
                                "  if (els[i].textContent.trim() === 'Documentos') {" +
                                "    els[i].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));" +
                                "  }" +
                                "}"
                        );
                    }
                }
        );
    }
}
