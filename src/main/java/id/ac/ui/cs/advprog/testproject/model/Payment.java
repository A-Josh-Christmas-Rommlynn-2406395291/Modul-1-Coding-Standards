package id.ac.ui.cs.advprog.testproject.model;

import id.ac.ui.cs.advprog.testproject.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class Payment {
    @Getter @Setter
    private String id;
    @Getter @Setter
    private String method;
    @Getter
    private String status;
    @Getter @Setter
    private Map<String, String> paymentData;

    Payment() {

    }

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = PaymentStatus.PENDING.getValue();
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data is empty");
        }
        else {
            this.paymentData = paymentData;
        }
    }

    public void setStatus(String status) {
        if (status.contains("SUCCESS")) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else if (status.contains("FAILED")) {
            this.status = PaymentStatus.FAILED.getValue();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
