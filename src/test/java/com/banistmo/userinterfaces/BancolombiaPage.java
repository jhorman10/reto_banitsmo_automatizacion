package com.banistmo.userinterfaces;

import com.banistmo.infrastructure.web.MenuInteractions;
import net.serenitybdd.screenplay.targets.Target;

public final class BancolombiaPage {

    private BancolombiaPage() {}

    public static final Target MENU_PRODUCTOS_SERVICIOS = MenuInteractions.menuButton();

    public static final Target DESKTOP_SUBMENU = MenuInteractions.submenuContainer();

    public static Target menuOptionDesktop(String name) {
        return MenuInteractions.desktopOption(name);
    }
}
