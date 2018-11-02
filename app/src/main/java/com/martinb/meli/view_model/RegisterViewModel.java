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

public class RegisterViewModel extends ViewModel {

    private static final String ERROR_MSJ = "description";

    private MutableLiveData<String> token = new MutableLiveData<>();
    private String errorMsj = null;

    public LiveData<String> signup(String email, String password, String displayName, String phone) {
        User user = new User(email, password, displayName, phone);

        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Void> call = appserverRequests.signup(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    handleGoodRequest(response);
                } else {
                    handleBadRequest(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                token.setValue(null);
                errorMsj = t.getMessage();
            }
        });
        return token;
    }

    private void handleGoodRequest(Response<Void> response) {
        okhttp3.Headers headers = response.headers();
        token.setValue( headers.get("Bearer") );
    }

    private void handleBadRequest(Response<Void> response) {
        token.setValue(null);
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
}
