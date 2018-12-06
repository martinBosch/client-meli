package com.martinb.meli.network.callback;

import android.graphics.Bitmap;

import com.martinb.meli.model.ImageManager;
import com.martinb.meli.model.ProductItem;

import java.util.ArrayList;

import retrofit2.Response;

public class ProductsPublishedCallback extends BaseCallBack<ArrayList<ProductItem>,
        ArrayList<ProductItem>> {

    private String refreshToken;

    @Override
    protected void handleGoodRequest(Response<ArrayList<ProductItem>> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        ArrayList<ProductItem> products = response.body();
        for (ProductItem product : products) {
            if (product.haveThunbnail()) {
                Bitmap image = ImageManager.getDecodeImage(product.getEncodedThumbnail());
                product.setThumbnail(image);
            }
        }
        this.data.setValue(products);
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
