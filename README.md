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
| JUnit Platform | 5.11.4 |
| PDFBox | 3.0.7 |
| Hamcrest | 3.0 |
| SLF4J | 2.0.18 |

## Estructura del Proyecto

```
src/test/
├── java/com/banistmo/
│   ├── constants/              # Centraliza todos los strings del proyecto
│   │   ├── Labels.java         #   Etiquetas de UI y textos de validacion
│   │   ├── Messages.java       #   Mensajes de log y error
│   │   ├── Scripts.java        #   Scripts JavaScript
│   │   ├── Selectors.java      #   Selectores CSS/XPath
│   │   └── Urls.java           #   URLs del sitio y del PDF
│   ├── interactions/           # Operaciones de bajo nivel (Screenplay Interactions)
│   │   ├── ClickAccordionByLabel.java
│   │   └── HideOverlay.java
│   ├── tasks/                  # Acciones de alto nivel (Screenplay Tasks)
│   │   ├── NavigateTo.java
│   │   ├── SelectFromMenu.java
│   │   ├── ExpandAccordion.java
│   │   └── DownloadDocument.java
│   ├── questions/              # Validaciones (Screenplay Questions)
│   │   └── ThePDF.java
│   ├── userinterfaces/         # Page Objects
│   │   └── BancolombiaPage.java
│   ├── runners/
│   │   └── CucumberTestSuite.java
│   └── stepdefinitions/
│       ├── BancolombiaSteps.java
│       └── ParameterDefinitions.java
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

## Principios de Diseño Aplicados

### SOLID
- **SRP**: Cada clase tiene una única responsabilidad (ej: `HideOverlay` solo oculta overlays, `ThePDF` solo valida PDFs)
- **OCP**: Las step definitions usan `Map<String,Runnable>` en vez de `if/else`; agregar una nueva opción no requiere modificar el método, solo agregar una entrada al mapa
- **DIP**: Tasks e Interactions dependen de abstracciones de Serenity (`Performable`), no de implementaciones concretas

### Eliminación de Magic-Strings
Cada string del proyecto vive en una constante dentro del paquete `constants/`:
- **`Urls`**: URLs del sitio y del recurso PDF
- **`Labels`**: Nombres de elementos de UI y textos de validación
- **`Selectors`**: IDs, clases CSS y templates XPath
- **`Scripts`**: Scripts JavaScript con métodos generadores para inyectar parámetros
- **`Messages`**: Mensajes de log y error

### Strategy Pattern
Las step definitions no usan condicionales (`if/else`). En su lugar, se usa `Map<String,Runnable>` que mapea cada label a su acción correspondiente. Para agregar una nueva opción solo se agrega una entrada al mapa.

```
NAVIGATION_ACTIONS = Map.of(
    Labels.INVERSIONES,       -> SelectFromMenu.option("Inversiones")
    Labels.INVERSION_VIRTUAL, -> Open.url(...) + HideOverlay.now()
)
```

## Ejecución

```bash
./gradlew clean test
```

Los reportes se generan en:
- Serenity: `target/site/serenity/index.html`
- JUnit: `build/reports/tests/test/index.html`

## Desafíos Técnicos Resueltos

1. **Overlay persistente**: El portal Liferay usa `bc-modal-overlay` que bloquea clics de Selenium. Se oculta con JavaScript via `HideOverlay` interaction.
2. **Acordeón dinámico**: El componente `mlAccordion` (Vue/React) no responde a `Selenium.click()` ni `JavaScriptClick`. Se usa `dispatchEvent(new MouseEvent('click'))` via `ClickAccordionByLabel`.
3. **Validación PDF**: PDF creado con Illustrator; el texto no siempre es extraíble. Se valida mediante metadatos (título + autor) con PDFBox 3.0.7 como primera opción, con fallback a extracción de texto.
4. **Runner moderno**: Uso de JUnit Platform `@Suite` en lugar del deprecated `CucumberWithSerenity`.
5. **Magic-strings**: Todos los strings están centralizados en `constants/`, cero literales sueltos en el código.

## Entrega

Ver los informes completos:
- [`INFORME.md`](INFORME.md) — Informe detallado en Markdown (recomendado, incluye capturas del reporte Serenity)
- [`informe_reto_automatizacion.txt`](informe_reto_automatizacion.txt) — Informe en texto plano

Ambos incluyen:
- Pasos realizados con explicaciones técnicas
- Comandos de Git utilizados
- [Repositorio en GitHub](https://github.com/jhorman10/reto_banitsmo_automatizacion)
