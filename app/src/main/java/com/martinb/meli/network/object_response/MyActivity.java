package com.martinb.meli.network.object_response;

public class MyActivity {

    private String _id;
    private String product_name;
    private Integer units;
    private Float value;
    private String delivery_status;
    private String payment_status;

    public String getActivityId() {
        return _id;
    }

    public String getProductName() {
        return product_name;
    }

    public Integer getUnits() {
        return units;
    }

    public Float getValue() {
        return value;
    }

    public String getDeliveryStatus() {
        return delivery_status;
    }

    public String getPaymentStatus() {
        return payment_status;
    }
}
