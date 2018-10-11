package com.martinb.meli.network.object_response;

import com.martinb.meli.model.ProductItem;

import java.util.ArrayList;

public class ProductPublishedResponse {

    private String token;
    private ArrayList<ProductItem> products;

    public ProductPublishedResponse(String token, ArrayList<ProductItem> products) {
        this.token = token;
        this.products = products;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<ProductItem> getProducts() {
        return products;
    }
}
