package id.ac.ui.cs.advprog.testproject.service;

import id.ac.ui.cs.advprog.testproject.model.Order;
import id.ac.ui.cs.advprog.testproject.model.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    Payment addPayment(Order order, String method, Map<String, String> paymentData);
    Payment setStatus(Payment payment, String status);
    Payment getPayment(String paymentId);
    List<Payment> getAllPayments();
}