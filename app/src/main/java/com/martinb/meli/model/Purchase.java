package com.martinb.meli.model;

import java.io.Serializable;

public class Purchase implements Serializable {

    private String productId;
    private Float price;
    private Integer amount;

    private String shipping_method;
    private String destination_address;
    private String destination_latitude;
    private String destination_longitude;
    private Float delivery_cost;

    private String payment_method;
    private Integer card_number;
    private String card_holder;
    private String expiration_date;
    private Integer card_cvc;

    public String getProductId() {
        return productId;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getShipping_method() {
        return shipping_method;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public String getDestination_latitude() {
        return destination_latitude;
    }

    public String getDestination_longitude() {
        return destination_longitude;
    }

    public Float getDelivery_cost() {
        return delivery_cost;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public Integer getCard_number() {
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

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setShipping_method(String shipping_method) {
        this.shipping_method = shipping_method;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }

    public void setDestination_latitude(String destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public void setDestination_longitude(String destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public void setDelivery_cost(Float delivery_cost) {
        this.delivery_cost = delivery_cost;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public void setCard_number(Integer card_number) {
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
}
