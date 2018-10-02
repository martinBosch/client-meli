package com.martinb.meli.network;

import retrofit2.Retrofit;

public class AppServerRequestFactory {

    private static String url = "https://app-server-meli.herokuapp.com";
    private static AppServerRequests requests = null;

    public static AppServerRequests getInstance() {
        if (requests == null) {
            Retrofit retrofit = RetrofitClient.getClient(url);
            requests = retrofit.create(AppServerRequests.class);
        }
        return requests;
    }
}
