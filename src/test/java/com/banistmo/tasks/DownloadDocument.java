package com.banistmo.tasks;

import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class DownloadDocument {

    public static Performable reglamentoInversionVirtual() {
        return Task.where("{0} descarga el reglamento de inversion virtual",
                Open.url("https://media.ffycdn.net/us/grupo-bancolombia-arquitectura-y-contenidos/qciA7CFfPVJQrMx85nux.pdf")
        );
    }
}
