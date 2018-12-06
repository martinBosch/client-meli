package com.martinb.meli.network.object_request;

public class Payment {

    private String payment_method;
    private String card_number;
    private String card_holder;
    private String card_expiration_year;
    private String card_expiration_month;
    private Integer card_cvc;

    public Payment(String payment_method, String card_number, String card_holder,
                   String expiration_year, String expiration_month, Integer card_cvc) {
        this.payment_method = payment_method;
        this.card_number = card_number;
        this.card_holder = card_holder;
        this.card_expiration_year = expiration_year;
        this.card_expiration_month = expiration_month;
        this.card_cvc = card_cvc;
    }
}
