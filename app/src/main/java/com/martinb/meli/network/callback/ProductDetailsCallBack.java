package com.martinb.meli.network.callback;

import android.graphics.Bitmap;

import com.martinb.meli.model.ImageManager;
import com.martinb.meli.network.object_request.Product;

import java.util.ArrayList;

import retrofit2.Response;

public class ProductDetailsCallBack extends BaseCallBack<Product, Product> {

    private String refreshToken = null;

    @Override
    protected void handleGoodRequest(Response<Product> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        Product product = response.body();
        ArrayList<Bitmap> images = new ArrayList<>();
        for (String encodedImage : product.getEncodedImages()) {
            Bitmap image = ImageManager.getDecodeImage(encodedImage);
            images.add(image);
        }
        product.setImages(images);
        this.data.setValue(product);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
