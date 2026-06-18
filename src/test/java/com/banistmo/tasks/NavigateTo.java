package com.banistmo.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class NavigateTo {

    public static Performable bancolombiaPersonasPage() {
        return Task.where("{0} navega a la pagina de personas de Bancolombia",
                Open.url("https://www.bancolombia.com/personas")
        );
    }
}
