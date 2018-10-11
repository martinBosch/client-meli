package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.network.object_request.PublishRequest;
import com.martinb.meli.network.object_response.PublishResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublishProductViewModel extends ViewModel {

    private static final String ERROR_MSJ = "description";

    private MutableLiveData<Product> data = new MutableLiveData<>();
    private String errorMsj = null;
    private String token;

    public LiveData<Product> publish(String token, String name, String description,
                                     ArrayList<String> images, float price,
                                     String category, String ubication, int units) {
        Product product = new Product(name, description, images, price, category, ubication, units);

        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<PublishResponse> call = appserverRequests.publish("Bearer " + token, new PublishRequest(product));

        call.enqueue(new Callback<PublishResponse>() {
            @Override
            public void onResponse(Call<PublishResponse> call, Response<PublishResponse> response) {
                if (response.isSuccessful()) {
                    handleGoodRequest(response);
                } else {
                    handleBadRequest(response);
                }
            }

            @Override
            public void onFailure(Call<PublishResponse> call, Throwable t) {
                data.setValue(null);
                errorMsj = t.getMessage();
            }
        });
        return data;
    }

    private void handleGoodRequest(Response<PublishResponse> response) {
        PublishResponse publishResponse = response.body();
        data.setValue(publishResponse.getProduct());
        token = publishResponse.getToken();
    }

    private void handleBadRequest(Response<PublishResponse> response) {
        data.setValue(null);
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
