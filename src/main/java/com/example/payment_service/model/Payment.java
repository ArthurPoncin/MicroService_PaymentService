package com.example.payment_service.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long registrationId;

    private BigDecimal montant;

    private String devise;

    private String statut;

    private String reference;

    private String paymentMethod;

    public Payment() {
    }

    public Payment(Long registrationId, BigDecimal montant, String devise, String statut, String reference) {
        this.registrationId = registrationId;
        this.montant = montant;
        this.devise = devise;
        this.statut = statut;
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public String getDevise() {
        return devise;
    }

    public String getStatut() {
        return statut;
    }

    public String getReference() {
        return reference;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
