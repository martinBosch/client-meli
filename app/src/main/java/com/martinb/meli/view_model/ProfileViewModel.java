package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.object_request.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private static final String ERROR_MSJ = "description";

    private MutableLiveData<User> user = new MutableLiveData<>();
    private String errorMsj = null;
    private String token;

    public LiveData<User> getUserInfo(String token, String userId) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<User> call = appserverRequests.profile("Bearer " + token, userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    handleGoodRequest(response);
                } else {
                    handleBadRequest(response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                user.setValue(null);
                errorMsj = t.getMessage();
            }
        });
        return user;
    }

    public void handleGoodRequest(Response<User> response) {
        okhttp3.Headers headers = response.headers();
        this.token = headers.get("Bearer");

        user.setValue( response.body() );
    }

    public void handleBadRequest(Response<User> response) {
        this.user.setValue(null);
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
