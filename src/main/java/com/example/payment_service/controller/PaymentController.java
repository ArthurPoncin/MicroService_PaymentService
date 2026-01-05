package com.example.payment_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.payment_service.model.Payment;
import com.example.payment_service.service.PaymentService;

@CrossOrigin(originPatterns = "http://localhost:*")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    // Créer un paiement
    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return service.createPayment(payment);
    }

    // Récupérer un paiement
    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {
        return service.getPayment(id);
    }

    // Récupérer l'historique d'une inscription
    @GetMapping("/registration/{regId}")
    public List<Payment> getByRegistration(@PathVariable Long regId) {
        return service.getPaymentsByRegistration(regId);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return service.getAllPayments();
    }

    // Paiement Stripe ou Paypal
    @PostMapping("/{id}/process")
    public Payment processPayment(@PathVariable Long id) {
        return service.processPayment(id);
    }

}