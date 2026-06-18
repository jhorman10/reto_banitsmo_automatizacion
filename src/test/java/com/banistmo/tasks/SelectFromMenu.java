package com.banistmo.tasks;

import com.banistmo.userinterfaces.BancolombiaPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectFromMenu {

    public static Performable menuProductosServicios() {
        return Task.where("{0} selecciona Productos & Servicios",
                WaitUntil.the(BancolombiaPage.MENU_PRODUCTOS_SERVICIOS, isClickable()),
                JavaScriptClick.on(BancolombiaPage.MENU_PRODUCTOS_SERVICIOS),
                WaitUntil.the(BancolombiaPage.menuOptionDesktop("Inversiones"), isVisible())
        );
    }

    public static Performable option(String optionName) {
        return Task.where("{0} selecciona " + optionName,
                WaitUntil.the(BancolombiaPage.menuOptionDesktop(optionName), isVisible()),
                JavaScriptClick.on(BancolombiaPage.menuOptionDesktop(optionName))
        );
    }
}
