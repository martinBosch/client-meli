package com.martinb.meli.network;

import android.content.Context;
import android.widget.Toast;

import com.martinb.meli.activity.RegisterActivity;
import com.martinb.meli.authentication.AccountAuthenticator;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpCallback implements Callback<UserResponse> {

    private RegisterActivity registerActivity;
    private Context registerContext;

    public SignUpCallback(RegisterActivity registerActivity, Context registerContext) {
        this.registerActivity = registerActivity;
        this.registerContext = registerContext;
    }

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        if (response.isSuccessful()) {
            UserResponse r = response.body();
            String email = r.getEmail();
            String password = r.getPassword();
            String token = r.getToken();
            AccountAuthenticator.createAccount(registerContext, email, password, token);
            registerActivity.goMainScreen();
        } else {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                String errorMessage = jObjError.getString("description");
                Toast.makeText(registerContext, errorMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(registerContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        Toast.makeText(registerContext, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
