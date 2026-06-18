package com.banistmo.stepdefinitions;

import com.banistmo.questions.ThePDF;
import com.banistmo.tasks.DownloadDocument;
import com.banistmo.tasks.ExpandAccordion;
import com.banistmo.tasks.NavigateTo;
import com.banistmo.tasks.SelectFromMenu;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static org.hamcrest.CoreMatchers.is;

public class BancolombiaSteps {

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
        if (optionName.equals("Inversiones")) {
            actor().attemptsTo(SelectFromMenu.option(optionName));
        } else if (optionName.equals("Inversion Virtual")) {
            actor().attemptsTo(
                    Open.url("https://www.bancolombia.com/personas/productos-servicios/inversiones/inversion-virtual"),
                    net.serenitybdd.screenplay.actions.Evaluate.javascript(
                            "document.querySelectorAll('.bc-modal-overlay, [data-bc-modal-hidden]').forEach(el => el.style.display='none')"
                    )
            );
        }
    }

    @And("hace clic en {string}")
    public void haceClicEn(String elementName) {
        if (elementName.equals("Documentos")) {
            actor().attemptsTo(ExpandAccordion.documentosSection());
        } else {
            Target element = Target.the("Element " + elementName)
                    .located(By.xpath("//*[contains(text(),'" + elementName + "')]"));
            actor().attemptsTo(
                    Scroll.to(element),
                    WaitUntil.the(element, isClickable()),
                    Click.on(element)
            );
        }
    }

    @And("descarga el documento {string}")
    public void descargaElDocumento(String documentName) {
        actor().attemptsTo(DownloadDocument.reglamentoInversionVirtual());
    }

    @Then("el documento PDF debe ser el correcto")
    public void elDocumentoPDFDebeSerElCorrecto() {
        actor().should(
                seeThat(ThePDF.containsText("Reglamento Inversion Virtual Bancolombia"), is(true))
        );
    }

    private Actor actor() {
        return OnStage.theActorInTheSpotlight();
    }
}
