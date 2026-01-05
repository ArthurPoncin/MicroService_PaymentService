package com.example.payment_service.processor;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.example.payment_service.processor.PaymentProcessor;

@Component
public class PaypalProcessor implements PaymentProcessor {

    @Override
    public boolean process(BigDecimal amount, String reference) {
        System.out.println(">>> Appel API PayPal...");
        if (reference == null || !reference.startsWith("EMAIL-")) {
            System.out.println(">>> PayPal : Refusé (Format email invalide)");
            return false;
        }
        return true;
    }

    @Override
    public String getProviderName() {
        return "PAYPAL";
    }
}