package com.example.payment_service.Unitaires;

import com.example.payment_service.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class PaymentServiceTest {

    private final PaymentService service;

    @Autowired
    public PaymentServiceTest(PaymentService paymentService) {
        this.service = paymentService;
    }

    @Test
    void shouldReturnTTCAmount() {
        BigDecimal montantHT = new BigDecimal("100.0");
        BigDecimal tauxTVA = new BigDecimal("0.20");
        BigDecimal expectedAmount = new BigDecimal("120.0");

        BigDecimal actualMontantTotal = service.calculerMontantTotal(montantHT, tauxTVA);

        assertEquals(0, actualMontantTotal.compareTo(expectedAmount),
                "Le calcul du montant TTC n'est pas correct.");
    }

    @Test
    void shouldReturnSameAmount() {
        BigDecimal montantHT = new BigDecimal("100.0");
        BigDecimal tauxTVA = new BigDecimal("0.00");
        BigDecimal expectedAmount = new BigDecimal("100.0");

        BigDecimal actualMontantTotal = service.calculerMontantTotal(montantHT, tauxTVA);

        assertEquals(0, actualMontantTotal.compareTo(expectedAmount),
                "Le prix doit être inchangé avec un taux de TVA de 0%");
    }

    @Test
    void ShouldReturnFalse() {
        BigDecimal montantHT = new BigDecimal("100.0");
        BigDecimal tauxTVA = new BigDecimal("0.00");
        BigDecimal expectedAmount = new BigDecimal("120.0");

        BigDecimal actualMontantTotal = service.calculerMontantTotal(montantHT, tauxTVA);

        assertNotEquals(0, actualMontantTotal.compareTo(expectedAmount),
                "0% de TVA doit retourner le même prix");
    }
}