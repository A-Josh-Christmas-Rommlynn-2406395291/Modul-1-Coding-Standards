package id.ac.ui.cs.advprog.testproject;

import id.ac.ui.cs.advprog.testproject.model.Order;
import id.ac.ui.cs.advprog.testproject.model.Payment;
import id.ac.ui.cs.advprog.testproject.model.Product;
import id.ac.ui.cs.advprog.testproject.repository.OrderRepository;
import id.ac.ui.cs.advprog.testproject.repository.PaymentRepository;
import id.ac.ui.cs.advprog.testproject.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CashOnDeliveryTest {
    private static final String PAYMENT_METHOD = "Cash On Delivery";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DELIVERY_FEE = "deliveryFee";

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
    }

    @Test
    void testAddPaymentWithValidCashOnDeliveryDataShouldSetStatusSuccess() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "Jl. Mawar No. 10");
        paymentData.put(KEY_DELIVERY_FEE, "15000");
        createCODPaymentAndSetStatus(paymentData, "SUCCESS", "SUCCESS");
        verify(paymentRepository, times(2)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testAddPaymentWithNullAddressShouldSetStatusFailed() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, null);
        paymentData.put(KEY_DELIVERY_FEE, "15000");
        createCODPaymentAndSetStatus(paymentData, "REJECTED", "FAILED");
    }

    @Test
    void testAddPaymentWithEmptyAddressShouldSetStatusFailed() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "");
        paymentData.put(KEY_DELIVERY_FEE, "15000");
        createCODPaymentAndSetStatus(paymentData, "REJECTED", "FAILED");
    }

    @Test
    void testAddPaymentWithNullDeliveryFeeShouldSetStatusFailed() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "Jl. Mawar No. 10");
        paymentData.put(KEY_DELIVERY_FEE, null);
        createCODPaymentAndSetStatus(paymentData, "REJECTED", "FAILED");
    }

    @Test
    void testAddPaymentWithEmptyDeliveryFeeShouldSetStatusFailed() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(KEY_ADDRESS, "Jl. Mawar No. 10");
        paymentData.put(KEY_DELIVERY_FEE, "");
        createCODPaymentAndSetStatus(paymentData, "REJECTED", "FAILED");
    }

    @Test
    public void createCODPaymentAndSetStatus(
            Map<String, String> paymentData,
            String codStatus,
            String expectedPaymentStatus
    ) {
        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(new Payment(order.getId(), PAYMENT_METHOD, paymentData))
                .when(paymentRepository).save(any(Payment.class));

        Payment createdPayment = paymentService.addPayment(order, PAYMENT_METHOD, paymentData);
        Payment updatedPayment = paymentService.setStatus(createdPayment, codStatus);

        assertEquals(expectedPaymentStatus, updatedPayment.getStatus());
    }
}
