package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.martinb.meli.model.ImageManager;
import com.martinb.meli.model.ProductItem;
import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.object_response.ProductPublishedResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private static final String ERROR_MSJ = "description";

    private MutableLiveData<ArrayList<ProductItem>> products = new MutableLiveData<>();
    private String errorMsj = null;
    private String token;

    public LiveData<ArrayList<ProductItem>> getPublishedProduct(String token) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<ProductPublishedResponse> call = appserverRequests.productsPublished("Bearer " + token);

        call.enqueue(new Callback<ProductPublishedResponse>() {
            @Override
            public void onResponse(Call<ProductPublishedResponse> call, Response<ProductPublishedResponse> response) {
                if (response.isSuccessful()) {
                    handleGoodRequest(response);
                } else {
                    handleBadRequest(response);
                }
            }

            @Override
            public void onFailure(Call<ProductPublishedResponse> call, Throwable t) {
                products.setValue(null);
                errorMsj = t.getMessage();
            }
        });
        return products;
    }

    private void handleGoodRequest(Response<ProductPublishedResponse> response) {
        ProductPublishedResponse productPublished = response.body();

        this.token = productPublished.getToken();
        ArrayList<ProductItem> products = productPublished.getProducts();
        for (ProductItem product : products) {
            Bitmap image = ImageManager.getDecodeImage(product.getEncodedThumbnail());
            product.setThumbnail(image);
        }
        this.products.setValue(products);
    }

    private void handleBadRequest(Response<ProductPublishedResponse> response) {
        products.setValue(null);
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            errorMsj = jObjError.getString(ERROR_MSJ);
        } catch (Exception e) {
            errorMsj = e.getMessage();
        }
    }

    public String getErrorMsj() {
        return errorMsj;
    }

    public String getToken() {
        return token;
    }
}
