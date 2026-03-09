package id.ac.ui.cs.advprog.testproject.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("Jalan Mock", "10000");
        paymentData.put("Jalan Escapia", "30000");
        payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "Cash On Delivery", paymentData);


    }

    @Test
    void testGetId() {
        assertEquals("13652556-012a-4c07-b546-54eb1396d79b", payment.getId());
    }

    @Test
    void testGetMethod() {
        assertEquals("Cash On Delivery", payment.getMethod());
    }

    @Test
    void testGetStatus() {
        assertEquals("PENDING", payment.getStatus());
    }

    @Test
    void testGetPaymentData() {
        assertEquals(2, payment.getPaymentData().size());
        assertEquals("10000", payment.getPaymentData().get("Jalan Mock"));
        assertEquals("30000", payment.getPaymentData().get("Jalan Escapia"));
    }

    @Test
    void testGetIdNegativeScenario() {
        assertNotEquals("40671819-9282-3939-1901-9aaabbc1092f", payment.getId());
    }

    @Test
    void testGetMethodNegativeScenario() {
        assertNotEquals("BANK TRANSFER", payment.getMethod());
    }

    @Test
    void testGetStatusNegativeScenario() {
        assertNotEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetPaymentDataCanOverwriteValue() {
        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("Jalan Dipomockia", "10000");

        payment.setPaymentData(newPaymentData);

        assertEquals(1, payment.getPaymentData().size());
        assertEquals("10000", payment.getPaymentData().get("Jalan Dipomockia"));
    }

    @Test
    void testConstructorThrowsExceptionWhenPaymentDataIsEmpty() {
        Map<String, String> emptyPaymentData = new HashMap<>();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Payment("13652556-012a-4c07-b546-54eb1396d79b", "Cash on Delivery", emptyPaymentData)
        );

        assertEquals("Payment data is empty", exception.getMessage());
    }

    @Test
    void testSetStatusWithValidStatus() {
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusWithInvalidStatusThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}
