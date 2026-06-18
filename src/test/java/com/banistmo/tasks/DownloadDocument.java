package com.banistmo.tasks;

import com.banistmo.constants.Messages;
import com.banistmo.constants.Urls;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public final class DownloadDocument {

    private DownloadDocument() {}

    public static Performable reglamentoInversionVirtual() {
        return Task.where(Messages.DOWNLOAD_DOCUMENT, Open.url(Urls.REGLAMENTO_PDF));
    }
}
