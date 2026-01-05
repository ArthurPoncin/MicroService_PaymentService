package com.example.payment_service.Integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.payment_service.controller.PaymentController;
import com.example.payment_service.model.Payment;
import com.example.payment_service.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = new Payment(102L, new BigDecimal("1440.0"), "EUR", "Pending", "Ref-Test");
    }

    @Test
    void shouldCreatePayment() throws Exception {
        when(service.createPayment(any(Payment.class))).thenReturn(testPayment);

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testPayment)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationId").value(102L));
    }

    @Test
    void shouldGetPaymentById() throws Exception {
        when(service.getPayment(102L)).thenReturn(testPayment);

        mockMvc.perform(get("/payments/102")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.devise").value("EUR"))
                .andExpect(jsonPath("$.statut").value("Pending"));
    }

    @Test
    void shouldProcessPayment() throws Exception {
        Payment paymentValide = new Payment(102L, new BigDecimal("1440.0"), "EUR", "SUCCESS", "Ref-Valid-123");
        paymentValide.setPaymentMethod("STRIPE");

        when(service.processPayment(102L)).thenReturn(paymentValide);

        mockMvc.perform(post("/payments/102/process")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut").value("SUCCESS"));
    }
}