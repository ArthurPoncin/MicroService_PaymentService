package com.example.payment_service.processor;

import java.math.BigDecimal;

public interface PaymentProcessor {
    boolean process(BigDecimal amount, String reference);

    String getProviderName();
}