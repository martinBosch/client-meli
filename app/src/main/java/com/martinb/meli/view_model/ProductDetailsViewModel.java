package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.martinb.meli.model.ImageManager;
import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.object_request.Product;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsViewModel extends ViewModel {

    private static final String ERROR_MSJ = "description";

    private MutableLiveData<Product> product = new MutableLiveData<>();
    private String errorMsj = null;
    private String token;

    public LiveData<Product> getProductDetails(String token, String productId) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Product> call = appserverRequests.productDetail("Bearer " + token, productId);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    handleGoodRequest(response);
                } else {
                    handleBadRequest(response);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                product.setValue(null);
                errorMsj = t.getMessage();
            }
        });
        return product;
    }

    private void handleGoodRequest(Response<Product> response) {
        okhttp3.Headers headers = response.headers();
        this.token = headers.get("Bearer");

        Product product = response.body();
        ArrayList<Bitmap> images = new ArrayList<>();
        for (String encodedImage : product.getEncodedImages()) {
            Bitmap image = ImageManager.getDecodeImage(encodedImage);
            images.add(image);
        }
        product.setImages(images);
        this.product.setValue(product);
    }

    private void handleBadRequest(Response<Product> response) {
        this.product.setValue(null);
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
