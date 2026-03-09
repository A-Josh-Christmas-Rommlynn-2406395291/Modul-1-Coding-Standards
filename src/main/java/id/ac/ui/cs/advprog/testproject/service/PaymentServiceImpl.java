package id.ac.ui.cs.advprog.testproject.service;

import id.ac.ui.cs.advprog.testproject.enums.OrderStatus;
import id.ac.ui.cs.advprog.testproject.model.Order;
import id.ac.ui.cs.advprog.testproject.model.Payment;
import id.ac.ui.cs.advprog.testproject.repository.OrderRepository;
import id.ac.ui.cs.advprog.testproject.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(order.getId(), method, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        String normalizedPaymentStatus = status;
        String mappedOrderStatus = null;

        if ("REJECTED".equals(status)) {
            normalizedPaymentStatus = "FAILED";
            mappedOrderStatus = OrderStatus.FAILED.getValue();
        } else if ("SUCCESS".equals(status)) {
            mappedOrderStatus = OrderStatus.SUCCESS.getValue();
        } else if ("FAILED".equals(status)) {
            mappedOrderStatus = OrderStatus.FAILED.getValue();
        }

        payment.setStatus(normalizedPaymentStatus);
        paymentRepository.save(payment);

        Order order = orderRepository.findById(payment.getId());
        if (order != null && mappedOrderStatus != null) {
            Order updatedOrder = new Order(
                    order.getId(),
                    order.getProducts(),
                    order.getOrderTime(),
                    order.getAuthor(),
                    mappedOrderStatus
            );
            orderRepository.save(updatedOrder);
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}