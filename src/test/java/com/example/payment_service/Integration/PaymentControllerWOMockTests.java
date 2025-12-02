package com.example.payment_service.Integration;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpStatus;

import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerWOMockTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PaymentRepository repository;
    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/payments";
        repository.deleteAll();
        repository.save(new Payment(102L, new BigDecimal("1440.0"), "EUR", "Pending", "Ref-Test"));
        repository.save(new Payment(103L, new BigDecimal("850.0"), "EUR", "Pending", "Ref-Test"));
    }

    @Test
    void shouldReturnOk200() {
        ResponseEntity<Payment[]> response = restTemplate.getForEntity(baseUrl, Payment[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnSizeTwo() {
        ResponseEntity<Payment[]> response = restTemplate.getForEntity(baseUrl, Payment[].class);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void shouldReturnPaymentWithCorrectDetails() {
        ResponseEntity<Payment[]> response = restTemplate.getForEntity(baseUrl, Payment[].class);
        assertThat(response.getBody()[0].getRegistrationId()).isEqualTo(102L);
        assertThat(response.getBody()[0].getMontant()).isEqualByComparingTo(new BigDecimal("1440.0"));
    }
}
