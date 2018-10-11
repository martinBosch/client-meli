package com.martinb.meli.network.object_response;

import com.martinb.meli.network.object_request.Product;

public class PublishResponse {

    private String token;
    private Product product;

    public PublishResponse(String token, Product product) {
        this.token = token;
        this.product = product;
    }

    public String getToken() {
        return token;
    }

    public Product getProduct() {
        return product;
    }
}
