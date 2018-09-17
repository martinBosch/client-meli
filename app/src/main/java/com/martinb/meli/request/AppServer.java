package com.martinb.meli.request;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppServer {

    private String url = "https://app-server-meli.herokuapp.com";
    private AppServerRequests appserverRequests;

    public AppServer() {
        Retrofit retrofit = RetrofitClient.getClient(url);
        appserverRequests = retrofit.create(AppServerRequests.class);
    }

    public void helloWord() {
        Call<String> call = appserverRequests.helloWord();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String r = response.body();
//                Log.i("REQUEST", r);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //Todo: Que es lo mejor que se puede hacer en este caso???
//                Log.i("REQUEST_ERROR", t.toString());
            }
        });
    }
}
