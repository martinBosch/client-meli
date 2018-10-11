package com.martinb.meli.network.object_response;

import com.martinb.meli.network.object_request.Product;

public class ProductDetailResponse {

    private String Token;
    private Product product;

    public String getToken() {
        return Token;
    }

    public Product getProduct() {
        return product;
    }
}
