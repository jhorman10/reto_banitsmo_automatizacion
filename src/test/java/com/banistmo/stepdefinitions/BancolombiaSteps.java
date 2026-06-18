package com.banistmo.stepdefinitions;

import com.banistmo.application.questions.ThePDF;
import com.banistmo.application.tasks.DownloadDocument;
import com.banistmo.application.tasks.ExpandAccordion;
import com.banistmo.application.tasks.NavigateTo;
import com.banistmo.application.tasks.SelectFromMenu;
import com.banistmo.domain.constants.Labels;
import com.banistmo.domain.constants.Urls;
import com.banistmo.infrastructure.web.OverlayHandler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

public class BancolombiaSteps {

    private static final java.util.Map<String, Runnable> NAVIGATION_ACTIONS = java.util.Map.of(
            Labels.INVERSIONES, () -> actor().attemptsTo(SelectFromMenu.option(Labels.INVERSIONES)),
            Labels.INVERSION_VIRTUAL, () -> actor().attemptsTo(
                    Open.url(Urls.INVERSION_VIRTUAL_PAGE),
                    OverlayHandler.hide()
            )
    );

    private static final java.util.Map<String, Runnable> CLICK_ACTIONS = java.util.Map.of(
            Labels.DOCUMENTOS, () -> actor().attemptsTo(ExpandAccordion.documentosSection())
    );

    @Given("el usuario se encuentra en la pagina de personas de Bancolombia")
    public void elUsuarioSeEncuentraEnLaPaginaDePersonas() {
        actor().attemptsTo(NavigateTo.bancolombiaPersonasPage());
    }

    @When("navega al menu {string}")
    public void navegaAlMenu(String menuName) {
        actor().attemptsTo(SelectFromMenu.menuProductosServicios());
    }

    @And("selecciona la opcion {string}")
    public void seleccionaLaOpcion(String optionName) {
        Runnable action = NAVIGATION_ACTIONS.get(optionName);
        if (action != null) {
            action.run();
        }
    }

    @And("hace clic en {string}")
    public void haceClicEn(String elementName) {
        Runnable action = CLICK_ACTIONS.get(elementName);
        if (action != null) {
            action.run();
        }
    }

    @And("descarga el documento {string}")
    public void descargaElDocumento(String documentName) {
        actor().attemptsTo(DownloadDocument.reglamentoInversionVirtual());
    }

    @Then("el documento PDF debe ser el correcto")
    public void elDocumentoPDFDebeSerElCorrecto() {
        actor().should(
                seeThat(ThePDF.isCorrect(), is(true))
        );
    }

    private static net.serenitybdd.screenplay.Actor actor() {
        return OnStage.theActorInTheSpotlight();
    }
}
