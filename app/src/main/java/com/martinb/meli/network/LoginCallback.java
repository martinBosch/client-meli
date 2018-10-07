package com.martinb.meli.network;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.martinb.meli.activity.HomeActivity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCallback implements Callback<AuthenticationResponse> {

    private Context loginContext;

    public LoginCallback(Context loginContext) {
        this.loginContext = loginContext;
    }

    @Override
    public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
        if (response.isSuccessful()) {
            AuthenticationResponse r = response.body();
//            String email = r.getEmail();
//            String password = r.getPassword();
//            String token = r.getToken();
//            AccountAuthenticator.createAccount(loginContext, email, password, token);
//            goMainScreen();
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
    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
        Toast.makeText(loginContext, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void goMainScreen() {
        Intent intent = new Intent(loginContext, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        loginContext.startActivity(intent);
    }

}
