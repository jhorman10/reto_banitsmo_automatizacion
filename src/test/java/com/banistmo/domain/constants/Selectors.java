package com.banistmo.domain.constants;

public final class Selectors {

    private Selectors() {}

    public static final String MENU_PRODUCTOS_ID = "menu-productos";
    public static final String DESKTOP_SUBMENU_CLASS = "desktop-submenu";
    public static final String MENU_OPTION_XPATH_TEMPLATE = "//a[contains(@id,'header-productos-') and contains(text(),'%s')]";
    public static final String OVERLAY_CSS = ".bc-modal-overlay";
    public static final String OVERLAY_DATA_ATTR = "[data-bc-modal-hidden]";
    public static final String ACCORDION_CLASSES = "accordion, Accordion, .cbc-heading__title, .cbc-heading, heading";

    public static String menuOptionXpath(String name) {
        return String.format(MENU_OPTION_XPATH_TEMPLATE, name);
    }
}
