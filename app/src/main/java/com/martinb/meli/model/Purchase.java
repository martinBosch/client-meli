package com.martinb.meli.model;

import com.martinb.meli.network.object_request.Payment;
import com.martinb.meli.network.object_request.Ubication;

import java.io.Serializable;

import static com.martinb.meli.activity.purchase.PurchaseShippingMethodsActivity.DELIVERY;

public class Purchase implements Serializable {

    private String productId;
    private Float price;
    private Integer units;

    private String shipping_method;
    private String destination_address;
    private Double destination_latitude;
    private Double destination_longitude;
    private Float delivery_cost;

    private String payment_method;
    private String card_number;
    private String card_holder;
    private String expiration_date;
    private Integer card_cvc;

    private static final int MONTH = 0;
    private static final int YEAR = 1;

    public String getProductId() {
        return productId;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getUnits() {
        return units;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public boolean isDelivery() {
        return shipping_method.equals(DELIVERY);
    }

    public String getDestination_address() {
        return destination_address;
    }

    public Double getDestination_latitude() {
        return destination_latitude;
    }

    public Double getDestination_longitude() {
        return destination_longitude;
    }

    public Float getDelivery_cost() {
        return delivery_cost;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public Integer getCard_cvc() {
        return card_cvc;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }

    public void setDestination_latitude(Double destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public void setDestination_longitude(Double destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public void setDelivery_cost(Float delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public void setCard_holder(String card_holder) {
        this.card_holder = card_holder;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public void setCard_cvc(Integer card_cvc) {
        this.card_cvc = card_cvc;
    }

    public Payment getPayment() {
        String[] expiration_date_split = this.expiration_date.split("/");
        String expiration_month = expiration_date_split[MONTH];
        String expiration_year = expiration_date_split[YEAR];

        return new Payment(this.payment_method, this.card_number, this.card_holder,
                expiration_year, expiration_month, this.card_cvc);
    }

    public Ubication getUbication() {
        return new Ubication(this.destination_address, this.destination_latitude, this.destination_longitude);
    }
}
