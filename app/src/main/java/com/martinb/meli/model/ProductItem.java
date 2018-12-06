package com.martinb.meli.model;

import android.graphics.Bitmap;

public class ProductItem {

    private String name;
    private String price;
    private String thumbnail = null;
    private String _id;

    private Bitmap thumbnail_decoded;

//    public ProductItem(String thumbnail, String name, String price, String _id) {
//        this.thumbnail = thumbnail;
//        this.name = name;
//        this.price = price;
//        this._id = _id;
//    }

    public Bitmap getThumbnail() {
        return this.thumbnail_decoded;
    }

    public String getEncodedThumbnail() {
        return thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return name;
    }

    public boolean haveThunbnail() {
        return thumbnail != null;
    }

    public void setThumbnail(Bitmap thumbnail_decoded) {
        this.thumbnail_decoded = thumbnail_decoded;
    }

    public String getId() {
        return _id;
    }

}
