package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.AuthenticationResponse;
import com.martinb.meli.network.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<AuthenticationResponse> data = new MutableLiveData<>();

    public LiveData<AuthenticationResponse> signup(String email, String password) {
        User user = new User(email, password, null);
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<User> call = appserverRequests.login(user);
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
                data.setValue(new AuthenticationResponse(null, t.getMessage()));
            }
        });
        return data;
    }

    private void handleGoodRequest(Response<User> response) {
        User user = response.body();
        data.setValue(new AuthenticationResponse(user, null));
    }

    private void handleBadRequest(Response<User> response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String errorMessage = jObjError.getString("description");
            data.setValue(new AuthenticationResponse(null, errorMessage));
        } catch (Exception e) {
            data.setValue(new AuthenticationResponse(null, e.getMessage()));
        }
    }
}
