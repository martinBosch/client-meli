package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.network.object_response.UserId;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private static final String ERROR_MSJ = "description";

    private MutableLiveData<String> token = new MutableLiveData<>();
    private String userId = null;
    private String errorMsj = null;

    public LiveData<String> login(String email, String password) {
        User user = new User(email, password, null, null);

        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<UserId> call = appserverRequests.login(user);
        call.enqueue(new Callback<UserId>() {
            @Override
            public void onResponse(Call<UserId> call, Response<UserId> response) {
                if (response.isSuccessful()) {
                    handleGoodRequest(response);
                } else {
                    handleBadRequest(response);
                }
            }

            @Override
            public void onFailure(Call<UserId> call, Throwable t) {
                token.setValue(null);
                errorMsj = t.getMessage();
            }
        });
        return token;
    }

    private void handleGoodRequest(Response<UserId> response) {
        userId = response.body().getUserId();

        okhttp3.Headers headers = response.headers();
        token.setValue( headers.get("Bearer") );
    }

    private void handleBadRequest(Response<UserId> response) {
        token.setValue(null);
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            errorMsj = jObjError.getString(ERROR_MSJ);
        } catch (Exception e) {
            errorMsj = e.getMessage();
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getErrorMsj() {
        return errorMsj;
    }
}
