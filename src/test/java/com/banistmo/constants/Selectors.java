package com.banistmo.constants;

public final class Selectors {

    private Selectors() {}

    public static final String MENU_PRODUCTOS_ID = "menu-productos";
    public static final String DESKTOP_SUBMENU_CLASS = "desktop-submenu";
    public static final String MENU_OPTION_XPATH_TEMPLATE = "//a[contains(@id,'header-productos-') and contains(text(),'%s')]";

    public static String menuOptionXpath(String name) {
        return String.format(MENU_OPTION_XPATH_TEMPLATE, name);
    }
}
