package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.martinb.meli.model.ImageManager;
import com.martinb.meli.model.ProductItem;
import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.ProductsPublishedCallback;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private ProductsPublishedCallback callback;

    public LiveData<ArrayList<ProductItem>> getPublishedProduct(String token) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<ArrayList<ProductItem>> call = appserverRequests.productsPublished("Bearer " + token);
        callback = new ProductsPublishedCallback();
        call.enqueue( callback );
        return callback.getData();
    }

    public String getRefreshToken() {
        return callback.getRefreshToken();
    }

    public String getErrorMsj() {
        return callback.getErrorMsj();
    }
}
