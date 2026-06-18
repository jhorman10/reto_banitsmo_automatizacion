package com.banistmo.infrastructure.web;

import com.banistmo.domain.constants.Selectors;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class MenuInteractions {

    private MenuInteractions() {}

    public static Target menuButton() {
        return Target.the("Menu Productos y Servicios")
                .located(By.id(Selectors.MENU_PRODUCTOS_ID));
    }

    public static Target submenuContainer() {
        return Target.the("Submenu desktop")
                .located(By.className(Selectors.DESKTOP_SUBMENU_CLASS));
    }

    public static Target desktopOption(String name) {
        return Target.the("Desktop menu option " + name)
                .located(By.xpath(Selectors.menuOptionXpath(name)));
    }
}
