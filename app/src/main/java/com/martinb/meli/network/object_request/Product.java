package com.martinb.meli.network.object_request;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Product {

    private String _id;
    private String name;
    private String description;
    private ArrayList<String> images;
    private ArrayList<Bitmap> desencodedImages;
    private float price;
    private String category;
    private String ubication;
    private int units;

    public Product(String name, String description, ArrayList<String> images,
                   float price, String category, String ubication, int units) {
        this.name = name;
        this.description = description;
        this.images = images;
        this.price = price;
        this.category = category;
        this.ubication = ubication;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getEncodedImages() {
        return images;
    }

    public ArrayList<Bitmap> getImages() {
        return desencodedImages;
    }

    public float getPrice() {
        return price;
    }

    public void setImages(ArrayList<Bitmap> images) {
        this.desencodedImages = images;
    }

    public String getCategory() {
        return category;
    }

    public String getUbication() {
        return ubication;
    }

    public int getUnits() {
        return units;
    }
}