# Informe Reto Técnico de Automatización
## Skill Hacking Banistmo

---

## 1. Principales Pasos Realizados

### 1.1 Análisis y Planificación

Se analizó el sitio web [https://www.bancolombia.com/personas](https://www.bancolombia.com/personas) identificando que es un portal Liferay con componentes Vue/React que renderizan el contenido dinámicamente. Esto implica que muchos elementos del DOM no están presentes en el HTML estático y requieren interacción con JavaScript para aparecer.

**Stack tecnológico definido:**

| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje base |
| Gradle | 9.5.1 | Build tool |
| Serenity BDD | 5.3.9 | Framework de automatización con reportes |
| Cucumber | 7.34.3 | Escenarios Gherkin legibles por negocio |
| Screenplay Pattern | — | Patrón de diseño para pruebas mantenibles |
| PDFBox | 3.0.7 | Validación de PDFs |
| JUnit Platform | 5.11.4 | Ejecutor de pruebas moderno |
| Hamcrest | 3.0 | Assertions |
| SLF4J | 2.0.18 | Logging |

### 1.2 Estructura del Proyecto

```
src/
└── test/
    ├── java/com/banistmo/
    │   ├── runners/
    │   │   └── CucumberTestSuite.java          # Runner JUnit Platform
    │   ├── stepdefinitions/
    │   │   ├── BancolombiaSteps.java            # Steps de Cucumber
    │   │   └── ParameterDefinitions.java        # Configuración actores
    │   ├── tasks/
    │   │   ├── NavigateTo.java                  # Abrir página principal
    │   │   ├── SelectFromMenu.java              # Navegación por menú
    │   │   ├── ExpandAccordion.java             # Expandir acordeón con JS
    │   │   └── DownloadDocument.java            # Abrir PDF
    │   ├── questions/
    │   │   └── ThePDF.java                      # Validar PDF
    │   └── userinterfaces/
    │       └── BancolombiaPage.java             # Localizadores
    └── resources/
        ├── features/
        │   └── bancolombia_inversiones.feature  # Escenario Gherkin
        ├── serenity.conf                        # Configuración Serenity
        └── junit-platform.properties            # Configuración JUnit
```

### 1.3 Dependencias (build.gradle)

- `serenity-core:5.3.9`, `serenity-cucumber:5.3.9`, `serenity-screenplay:5.3.9`
- `cucumber-java:7.34.3`, `cucumber-junit-platform-engine:7.34.3`
- `junit-platform-suite:1.11.4`, `junit-platform-launcher:1.11.4`
- `pdfbox:3.0.7` (uso de `Loader.loadPDF` en lugar de `PDDocument.load` — API v3)
- `hamcrest:3.0`, `slf4j-simple:2.0.18`

### 1.4 Flujo de Automatización

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

### 1.5 Explicación Técnica de Cada Paso

#### Paso 1: Navegar a la página de Personas
- **Tarea:** `NavigateTo`
- Usa `Open.url("https://www.bancolombia.com/personas")`
- Se eligió navegar directamente a `/personas` porque es la URL base del flujo.

#### Paso 2: Seleccionar "Productos & Servicios"
- **Tarea:** `SelectFromMenu`
- El menú principal está implementado con un botón que despliega opciones.
- Se usa `JavaScriptClick.on()` para evitar el overlay (`bc-modal-overlay`) que bloquea los clics de Selenium normales.
- El overlay es un elemento que cubre toda la pantalla después de ciertas interacciones con el menú.

#### Paso 3: Seleccionar "Inversiones"
- **Tarea:** `SelectFromMenu.option("Inversiones")`
- Misma estrategia: `JavaScriptClick` para evitar el overlay.
- Se espera a que el elemento sea visible con `WaitUntil`.

#### Paso 4: Navegar a "Inversión Virtual"
- Se usa `Open.url()` directamente a la URL completa porque el enlace no está disponible como un elemento clickeable estándar en el menú desplegable.
- Después de navegar, se ejecuta JavaScript para ocultar el overlay que persiste de la navegación anterior.

#### Paso 5: Hacer clic en "Documentos" (Acordeón)
- **Tarea:** `ExpandAccordion`
- La sección "Documentos" es un componente `mlAccordion` de Vue/React que renderiza su contenido dinámicamente.
- `Click.on()` no funciona por el overlay; `JavaScriptClick.on()` no dispara los event handlers del framework JavaScript.
- **Solución:** Se usa JavaScript para encontrar el elemento con texto "Documentos" y disparar un `MouseEvent` nativo con `dispatchEvent`, lo que activa correctamente el manejador del acordeón.

#### Paso 6: Descargar el PDF
- **Tarea:** `DownloadDocument`
- Se navega directamente a la URL del PDF:
  `https://media.ffycdn.net/us/grupo-bancolombia-arquitectura-y-contenidos/qciA7CFfPVJQrMx85nux.pdf`
- Esta URL se obtuvo del enlace "Conocer el reglamento" dentro del acordeón.

#### Paso 7: Validar el PDF
- **Pregunta:** `ThePDF`
- Se descarga el PDF desde la URL y se valida:
  - a) El título del documento contiene "Reglamento"
  - b) El autor contiene "Bancolombia"
- Se usa PDFBox 3.0.7 con `Loader.loadPDF()` (API moderna de PDFBox 3.x)
- Se maneja tanto metadatos como extracción de texto como respaldo.

### 1.6 Decisiones Técnicas Importantes

#### a) Serenity 5.3.9 + Cucumber 7
- Se usó la integración más reciente (`serenity-cucumber 5.3.9`).
- El runner usa JUnit Platform (`@Suite` + `@IncludeEngines("cucumber")`) en lugar del deprecado `@RunWith(CucumberWithSerenity.class)`.

#### b) Screenplay Pattern
- **Tasks:** Representan acciones del usuario (`NavigateTo`, `SelectFromMenu`, etc.)
- **Questions:** Representan verificaciones (`ThePDF`)
- **Step Definitions:** Orquestan los pasos usando el actor de Screenplay.
- **Page Objects:** Centralizan localizadores (`BancolombiaPage`).

#### c) Manejo del Overlay
- El sitio usa `bc-modal-overlay` que bloquea interacciones normales.
- Se usa `Evaluate.javascript` para ocultarlo después de navegaciones clave.
- Para clics en el acordeón, se usa `dispatchEvent(new MouseEvent('click'))` que es lo único que activa los handlers de Vue/React.

#### d) Validación de PDF
- Se valida mediante metadatos (título + autor) porque el PDF está generado desde Illustrator y su texto no siempre es extraíble con PDFBox.
- Se usa case-insensitive matching para robustez.

#### e) Configuración de Navegador
- Chrome headless para ejecución en CI.
- Resolución 1920×1080 para asegurar que los elementos responsive sean visibles.
- `autodownload=true` para que Serenity maneje las descargas.

---

## 2. Enlace al Repositorio

**Repositorio:** [https://github.com/jhorman10/reto_banitsmo_automatizacion](https://github.com/jhorman10/reto_banitsmo_automatizacion)

---

## 3. Comandos de Git Utilizados

```bash
# Verificar el estado inicial del repositorio local
git status

# Agregar archivos clave al staging (excluyendo build/ y target/ via .gitignore)
git add .gitignore build.gradle settings.gradle gradlew gradlew.bat gradle/ src/ README.md

# Crear el commit inicial con todo el código del proyecto
git commit -m "feat: implementacion automatizacion reto Banistmo

- Serenity BDD 5.3.9 + Cucumber 7.34.3 + Screenplay
- Navegacion por menu con manejo de overlay
- Expansion de acordeon Documentos via JavaScript
- Validacion de PDF con PDFBox 3.0.7
- Runner con JUnit Platform @Suite"

# Configurar la rama principal como 'main'
git branch -M main

# Agregar el repositorio remoto de GitHub
git remote add origin https://github.com/jhorman10/reto_banitsmo_automatizacion.git

# Verificar el remote configurado
git remote -v

# Subir el código a GitHub (primer push)
git push -u origin main

# Agregar el informe de entrega al staging
git add informe_reto_automatizacion.txt

# Commit de la actualización del informe con URL correcta
git commit -m "fix: actualiza URL del repositorio en informe"

# Subir cambios
git push

# Agregar capturas de pantalla del reporte Serenity
git add .gitignore screenshots/ informe_reto_automatizacion.txt

# Commit de las capturas
git commit -m "feat: agrega capturas de pantalla del reporte Serenity"

# Subir capturas
git push

# Ver historial completo de commits
git log --oneline

# Ver detalle de un commit específico
git show 4e45337 --stat
```

---

## 4. Resultados de la Ejecución

```bash
./gradlew clean test
```

**Resultado esperado:**
- Todos los pasos del escenario Cucumber deben pasar (PASSED)
- El reporte Serenity se genera en: `target/site/serenity/index.html`
- El reporte JUnit se genera en: `build/reports/tests/test/index.html`

**Evidencia de ejecución exitosa:**
```
CucumberTestSuite > Descargar y validar el Reglamento PASSED
BUILD SUCCESSFUL in ~30s
```

### Capturas del Reporte Serenity

| Vista | Captura |
|---|---|
| Vista general del reporte | ![OverView](screenshots/serenity_overview.png) |
| Resultado del test | ![TestResult](screenshots/serenity_test_result.png) |
| Pasos del escenario | ![Steps](screenshots/serenity_steps.png) |

---

## 5. Historial de Commits

```
4e45337 feat: agrega capturas de pantalla del reporte Serenity
92b66a6 fix: actualiza URL del repositorio en informe y README
1cc51a8 feat: implementacion automatizacion reto Banistmo
```

Repositorio completo: [https://github.com/jhorman10/reto_banitsmo_automatizacion](https://github.com/jhorman10/reto_banitsmo_automatizacion)
Rama: `main`

---

## 6. Notas Adicionales

- La URL del PDF se obtuvo inspeccionando el enlace "Conocer el reglamento" dentro del acordeón Documentos. Se usa directamente para robustez.
- El overlay (`bc-modal-overlay`) es un elemento persistente del portal Liferay que requiere ser manejado explícitamente en cada navegación.
- El acordeón `mlAccordion` usa event listeners de Vue/React; `dispatchEvent(new MouseEvent('click'))` es necesario porque `Selenium.click()` no activa los handlers del framework.
- PDFBox 3.0.7 cambió la API: usar `Loader.loadPDF(byte[])` en lugar del deprecado `PDDocument.load(File)`.

---

*Fin del Informe*
