package com.banistmo.application.tasks;

import com.banistmo.domain.constants.Messages;
import com.banistmo.userinterfaces.BancolombiaPage;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public final class SelectFromMenu {

    private SelectFromMenu() {}

    public static Performable menuProductosServicios() {
        return Task.where(Messages.SELECT_MENU,
                WaitUntil.the(BancolombiaPage.MENU_PRODUCTOS_SERVICIOS, isClickable()),
                JavaScriptClick.on(BancolombiaPage.MENU_PRODUCTOS_SERVICIOS),
                WaitUntil.the(BancolombiaPage.menuOptionDesktop("Inversiones"), isVisible())
        );
    }

    public static Performable option(String optionName) {
        return Task.where(Messages.SELECT_OPTION + optionName,
                WaitUntil.the(BancolombiaPage.menuOptionDesktop(optionName), isVisible()),
                JavaScriptClick.on(BancolombiaPage.menuOptionDesktop(optionName))
        );
    }
}
