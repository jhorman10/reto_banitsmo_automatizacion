package com.banistmo.tasks;

import com.banistmo.constants.Messages;
import com.banistmo.constants.Urls;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public final class NavigateTo {

    private NavigateTo() {}

    public static Performable bancolombiaPersonasPage() {
        return Task.where(Messages.NAVIGATE_TO_PERSONAS, Open.url(Urls.PERSONAS_PAGE));
    }
}
