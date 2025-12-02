package com.example.payment_service.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {

    // Constructeur
    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    // Créer un paiement
    public Payment createPayment(Payment payment) {
        return repository.save(payment);
    }

    // Récupérer un paiement
    public Payment getPayment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));
    }

    // Récupère tout les paiements d'une inscription
    public List<Payment> getPaymentsByRegistration(Long registrationId) {
        return repository.findByRegistrationId(registrationId);
    }

    // Met à jour le statut
    public Payment validatePayment(Long id) {
        Payment payment = getPayment(id);
        payment.setStatut("SUCCESS");
        payment.setReference("BANK-" + System.currentTimeMillis());
        return repository.save(payment);
    }

    // Calcul de la TVA
    public BigDecimal calculerMontantTotal(BigDecimal montantHT, BigDecimal tauxTVA) {
        BigDecimal montantTaxe = montantHT.multiply(tauxTVA);
        return montantHT.add(montantTaxe);
    }
}
