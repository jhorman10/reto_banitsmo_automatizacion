package com.banistmo.userinterfaces;

import com.banistmo.constants.Selectors;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class BancolombiaPage {

    private BancolombiaPage() {}

    public static final Target MENU_PRODUCTOS_SERVICIOS = Target
            .the("Menu Productos y Servicios")
            .located(By.id(Selectors.MENU_PRODUCTOS_ID));

    public static Target menuOptionDesktop(String name) {
        return Target.the("Desktop menu option " + name)
                .located(By.xpath(Selectors.menuOptionXpath(name)));
    }
}
