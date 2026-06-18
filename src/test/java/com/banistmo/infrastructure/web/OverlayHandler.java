package com.banistmo.infrastructure.web;

import com.banistmo.domain.constants.Scripts;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Evaluate;

public final class OverlayHandler {

    private OverlayHandler() {}

    public static Performable hide() {
        return Evaluate.javascript(Scripts.HIDE_OVERLAY);
    }
}
