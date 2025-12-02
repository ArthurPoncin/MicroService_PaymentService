Feature: Gestion des paiements

  Scenario: Création et validation d'un paiement
    Given un paiement pour l'inscription 105 avec un montant de 500.00 et la devise "EUR"
    When je valide le paiement
    Then le statut du paiement doit être "SUCCESS"