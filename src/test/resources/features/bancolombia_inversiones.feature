Feature: Validacion de documentos de inversion virtual Bancolombia

  Como usuario del portal de Bancolombia
  Quiero navegar hasta el reglamento de inversion virtual
  Para validar que el documento PDF descargado es el correcto

  Scenario: Descargar y validar el Reglamento de Inversion Virtual Bancolombia
    Given el usuario se encuentra en la pagina de personas de Bancolombia
    When navega al menu "Productos & Servicios"
    And selecciona la opcion "Inversiones"
    And selecciona la opcion "Inversion Virtual"
    And hace clic en "Documentos"
    And descarga el documento "Reglamento Inversion Virtual Bancolombia"
    Then el documento PDF debe ser el correcto
