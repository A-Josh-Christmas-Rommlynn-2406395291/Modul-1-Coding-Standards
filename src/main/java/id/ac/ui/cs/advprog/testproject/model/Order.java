package id.ac.ui.cs.advprog.testproject.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

public class Order {
    @Getter
    String id;
    @Getter
    List<Product> products;
    @Getter
    Long orderTime;
    @Getter
    String author;
    @Getter
    String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";

        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.products = products;
        }
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this(id, products, orderTime, author);

        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        if (Arrays.stream(statusList).noneMatch(item -> item.equalsIgnoreCase(status))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }

    public void setStatus(String status) {
        String[] statusList = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        if (Arrays.stream(statusList).noneMatch(item -> item.equalsIgnoreCase(status))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }


}
