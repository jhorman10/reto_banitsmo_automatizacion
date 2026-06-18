package com.banistmo.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class BancolombiaPage {

    public static final Target MENU_PRODUCTOS_SERVICIOS = Target
            .the("Menu Productos y Servicios")
            .located(By.id("menu-productos"));

    public static final Target DESKTOP_SUBMENU = Target
            .the("Submenu desktop")
            .located(By.className("desktop-submenu"));

    public static Target menuOptionDesktop(String name) {
        return Target.the("Desktop menu option " + name)
                .located(By.xpath("//a[contains(@id,'header-productos-') and contains(text(),'" + name + "')]"));
    }
}
