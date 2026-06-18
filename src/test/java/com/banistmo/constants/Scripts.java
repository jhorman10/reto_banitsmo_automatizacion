package com.banistmo.constants;

public final class Scripts {

    private Scripts() {}

    public static final String HIDE_OVERLAY =
            "document.querySelectorAll('.bc-modal-overlay, [data-bc-modal-hidden]').forEach(el => el.style.display='none')";

    private static final String FIND_VISIBLE_AND_CLICK =
            "var els = document.querySelectorAll('*');" +
            "for (var i = 0; i < els.length; i++) {" +
            "  if (els[i].offsetParent !== null && els[i].textContent.trim() === '%s') {" +
            "    els[i].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));" +
            "    return 'clicked: ' + els[i].tagName + '.' + (els[i].className || '');" +
            "  }" +
            "}" +
            "return 'no visible element found';";

    private static final String FIND_ALL_AND_CLICK =
            "var els = document.querySelectorAll('*');" +
            "for (var i = 0; i < els.length; i++) {" +
            "  if (els[i].textContent.trim() === '%s') {" +
            "    els[i].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));" +
            "    return;" +
            "  }" +
            "}";

    private static final String FIND_ACCORDION_AND_CLICK =
            "var els = document.querySelectorAll('[class*=\"accordion\"], [class*=\"Accordion\"], .cbc-heading__title, .cbc-heading, [class*=\"heading\"]');" +
            "for (var i = 0; i < els.length; i++) {" +
            "  if (els[i].textContent.trim() === '%s') {" +
            "    els[i].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));" +
            "  }" +
            "}";

    public static String findVisibleAndClick(String label) {
        return String.format(FIND_VISIBLE_AND_CLICK, label);
    }

    public static String findAllAndClick(String label) {
        return String.format(FIND_ALL_AND_CLICK, label);
    }

    public static String findAccordionAndClick(String label) {
        return String.format(FIND_ACCORDION_AND_CLICK, label);
    }
}
