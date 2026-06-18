package com.banistmo.stepdefinitions;

import com.banistmo.constants.Labels;
import com.banistmo.constants.Urls;
import com.banistmo.interactions.HideOverlay;
import com.banistmo.questions.ThePDF;
import com.banistmo.tasks.DownloadDocument;
import com.banistmo.tasks.ExpandAccordion;
import com.banistmo.tasks.NavigateTo;
import com.banistmo.tasks.SelectFromMenu;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;

import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

public class BancolombiaSteps {

    private static final Map<String, Runnable> NAVIGATION_ACTIONS = Map.of(
            Labels.INVERSIONES, () -> actor().attemptsTo(SelectFromMenu.option(Labels.INVERSIONES)),
            Labels.INVERSION_VIRTUAL, () -> actor().attemptsTo(
                    Open.url(Urls.INVERSION_VIRTUAL_PAGE),
                    HideOverlay.now()
            )
    );

    private static final Map<String, Runnable> CLICK_ACTIONS = Map.of(
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
        if (action != null) action.run();
    }

    @And("hace clic en {string}")
    public void haceClicEn(String elementName) {
        Runnable action = CLICK_ACTIONS.get(elementName);
        if (action != null) action.run();
    }

    @And("descarga el documento {string}")
    public void descargaElDocumento(String documentName) {
        actor().attemptsTo(DownloadDocument.reglamentoInversionVirtual());
    }

    @Then("el documento PDF debe ser el correcto")
    public void elDocumentoPDFDebeSerElCorrecto() {
        actor().should(seeThat(ThePDF.isCorrect(), is(true)));
    }

    private static net.serenitybdd.screenplay.Actor actor() {
        return OnStage.theActorInTheSpotlight();
    }
}
