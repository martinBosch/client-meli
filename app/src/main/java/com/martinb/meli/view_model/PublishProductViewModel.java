package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.ProductPublishCallBack;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.network.object_request.PublishRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublishProductViewModel extends ViewModel {

    private ProductPublishCallBack callback;

    public LiveData<String> publish(final String token, String name, String description,
                                     ArrayList<String> images, float price, String category,
                                    String ubication, Double latitude, Double longitude, int units) {

        Product product = new Product(name, description, images, price, category,
                ubication, latitude, longitude, units);

        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Void> call = appserverRequests.publish("Bearer " + token, new PublishRequest(product));
        callback = new ProductPublishCallBack();
        call.enqueue( callback );
        return callback.getData();
    }

    public String getErrorMsj() {
        return callback.getErrorMsj();
    }
}
