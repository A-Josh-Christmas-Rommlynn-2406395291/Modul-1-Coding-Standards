package id.ac.ui.cs.advprog.testproject;

import java.util.HashMap;
import java.util.Map;

public class CashOnDelivery {
    public static final String ADDRESS_KEY = "address";
    public static final String DELIVERY_FEE_KEY = "deliveryFee";
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_REJECTED = "REJECTED";

    public boolean isAddressEmpty(String address) {
        return address == null || address.isBlank();
    }

    public boolean isDeliveryFeeEmpty(String deliveryFee) {
        return deliveryFee == null || deliveryFee.isBlank();
    }

    public boolean isValidDeliveryFee(String deliveryFee) {
        if (isDeliveryFeeEmpty(deliveryFee)) {
            return false;
        }

        try {
            return Integer.parseInt(deliveryFee) > 0;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public boolean isValidCashOnDelivery(Map<String, String> paymentData) {
        if (paymentData == null) {
            return false;
        }

        String address = paymentData.get(ADDRESS_KEY);
        String deliveryFee = paymentData.get(DELIVERY_FEE_KEY);
        return !isAddressEmpty(address) && isValidDeliveryFee(deliveryFee);
    }

    public String resolveStatus(Map<String, String> paymentData) {
        return isValidCashOnDelivery(paymentData) ? STATUS_SUCCESS : STATUS_REJECTED;
    }

    public Map<String, String> paymentDataWithAddressAndDeliveryFee(String address, String deliveryFee) {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put(ADDRESS_KEY, address);
        paymentData.put(DELIVERY_FEE_KEY, deliveryFee);
        return paymentData;
    }
}
