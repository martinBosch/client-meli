package com.martinb.meli.network;

import android.app.Activity;
import android.content.Context;

import com.martinb.meli.activity.LoginActivity;
import com.martinb.meli.activity.RegisterActivity;

import retrofit2.Call;

public class AppServer {

    private AppServerRequests appserverRequests;

    private Activity activity;
    private Context context;

    public AppServer() {
        appserverRequests = AppServerRequestFactory.getInstance();
    }

    public void setContext(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void signup(String email, String password) {
        User user = new User(email, password);
        Call<UserResponse> call = appserverRequests.signup(user);
        call.enqueue(new SignUpCallback((RegisterActivity) activity, context));
    }

    public void login(String email, String password) {
        User user = new User(email, password);
        Call<UserResponse> call = appserverRequests.login(user);
        call.enqueue(new LoginCallback((LoginActivity) activity, context));
    }
}
