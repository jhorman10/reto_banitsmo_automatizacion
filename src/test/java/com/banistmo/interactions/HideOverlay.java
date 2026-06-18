package com.banistmo.interactions;

import com.banistmo.constants.Scripts;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Evaluate;

public final class HideOverlay {

    private HideOverlay() {}

    public static Performable now() {
        return Evaluate.javascript(Scripts.HIDE_OVERLAY);
    }
}
