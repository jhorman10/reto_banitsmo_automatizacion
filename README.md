# Reto Técnico Banistmo - Automatización Bancolombia

## Descripción

Automatización del flujo de descarga y validación del **Reglamento de Inversión Virtual Bancolombia** en el portal [bancolombia.com/personas](https://www.bancolombia.com/personas) usando **Serenity BDD + Screenplay + Cucumber + Java + Gradle**.

## Stack Tecnológico

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Gradle | 9.5.1 |
| Serenity BDD | 5.3.9 |
| Cucumber | 7.34.3 |
| JUnit Platform | 1.11.4 |
| PDFBox | 3.0.7 |
| Hamcrest | 3.0 |
| SLF4J | 2.0.18 |

## Estructura del Proyecto

```
src/test/
├── java/com/banistmo/
│   ├── runners/
│   │   └── CucumberTestSuite.java
│   ├── stepdefinitions/
│   │   ├── BancolombiaSteps.java
│   │   └── ParameterDefinitions.java
│   ├── tasks/
│   │   ├── NavigateTo.java
│   │   ├── SelectFromMenu.java
│   │   ├── ExpandAccordion.java
│   │   └── DownloadDocument.java
│   ├── questions/
│   │   └── ThePDF.java
│   └── userinterfaces/
│       └── BancolombiaPage.java
└── resources/
    ├── features/
    │   └── bancolombia_inversiones.feature
    ├── serenity.conf
    └── junit-platform.properties
```

## Escenario Gherkin

```gherkin
Feature: Validacion de documentos de inversion virtual Bancolombia

  Scenario: Descargar y validar el Reglamento de Inversion Virtual Bancolombia
    Given el usuario se encuentra en la pagina de personas de Bancolombia
    When navega al menu "Productos & Servicios"
    And selecciona la opcion "Inversiones"
    And selecciona la opcion "Inversion Virtual"
    And hace clic en "Documentos"
    And descarga el documento "Reglamento Inversion Virtual Bancolombia"
    Then el documento PDF debe ser el correcto
```

## Ejecución

```bash
./gradlew clean test
```

Los reportes se generan en:
- Serenity: `target/site/serenity/index.html`
- JUnit: `build/reports/tests/test/index.html`

## Desafíos Técnicos Resueltos

1. **Overlay persistente**: El portal Liferay usa `bc-modal-overlay` que bloquea clics de Selenium. Se oculta con JavaScript.
2. **Acordeón dinámico**: El componente `mlAccordion` (Vue/React) requiere `dispatchEvent(new MouseEvent('click'))` para expandirse.
3. **Validación PDF**: PDF creado con Illustrator; se valida mediante metadatos (título + autor) con PDFBox 3.0.7.
4. **Runner moderno**: Uso de JUnit Platform `@Suite` en lugar del deprecated `CucumberWithSerenity`.

## Entrega

Ver `informe_reto_automatizacion.txt` para el informe completo incluyendo:
- Pasos realizados con explicaciones
- Comandos de Git
- [Repositorio en GitHub](https://github.com/jhorman10/reto_banitsmo_automatizacion)
