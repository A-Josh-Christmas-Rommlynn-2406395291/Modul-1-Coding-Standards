package id.ac.ui.cs.advprog.testproject.repository;

import id.ac.ui.cs.advprog.testproject.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        payments = new ArrayList<>();


        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("Jalan Mock", "50000");
        paymentData2.put("Jalan Escapia", "120000");
        Payment payment2 = new Payment(
                "98f0ee15-c7ab-4d5a-a0f6-7c5304c8cf26",
                "Cash On Delivery",
                paymentData2
        );
        payments.add(payment2);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("Jalan Mock", "250000");
        Payment newPayment = new Payment(payment.getId(), "Cash On Delivery", newPaymentData);
        newPayment.setStatus("SUCCESS");

        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals("Cash On Delivery", findResult.getMethod());
        assertEquals("SUCCESS", findResult.getStatus());
        assertEquals(newPaymentData, findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(0).getId());
        assertEquals(payments.get(0).getId(), findResult.getId());
        assertEquals(payments.get(0).getMethod(), findResult.getMethod());
        assertEquals(payments.get(0).getStatus(), findResult.getStatus());
        assertEquals(payments.get(0).getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("40a1-6bed-1f1f-9978-ca3412789065");
        assertNull(findResult);
    }
}
