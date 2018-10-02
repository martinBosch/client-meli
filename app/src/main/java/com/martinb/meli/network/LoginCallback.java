package com.martinb.meli.network;

import android.content.Context;
import android.widget.Toast;

import com.martinb.meli.activity.LoginActivity;
import com.martinb.meli.authentication.AccountAuthenticator;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCallback implements Callback<UserResponse> {

    private LoginActivity loginActivity;
    private Context loginContext;

    public LoginCallback(LoginActivity loginActivity, Context loginContext) {
        this.loginActivity = loginActivity;
        this.loginContext = loginContext;
    }

    @Override
    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
        if (response.isSuccessful()) {
            UserResponse r = response.body();
            String email = r.getEmail();
            String password = r.getPassword();
            String token = r.getToken();
            AccountAuthenticator.createAccount(loginContext, email, password, token);
            loginActivity.goMainScreen();
        } else {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                String errorMessage = jObjError.getString("description");
                Toast.makeText(loginContext, errorMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(loginContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(Call<UserResponse> call, Throwable t) {
        Toast.makeText(loginContext, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
