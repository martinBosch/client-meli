package com.martinb.meli.model;

public class Product {

    private int image;
    private String price;
    private String title;

    public Product(int image, String price, String title) {
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }
}
