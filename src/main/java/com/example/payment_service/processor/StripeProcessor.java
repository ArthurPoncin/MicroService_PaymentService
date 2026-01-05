package com.example.payment_service.processor;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class StripeProcessor implements PaymentProcessor {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @Override
    public boolean process(BigDecimal amount, String reference) {
        try {
            // Stripe utilise les centimes
            long amountInCents = amount.multiply(new BigDecimal("100")).longValue();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("eur")
                    .setDescription("Paiement ref: " + reference)
                    .addPaymentMethodType("card") // Carte de test
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            System.out.println(">>> Succès Stripe ! ID: " + intent.getId());
            return true;

        } catch (StripeException e) {
            System.err.println(">>> Erreur Stripe : " + e.getMessage());
            return false;
        }
    }

    @Override
    public String getProviderName() {
        return "STRIPE";
    }
}