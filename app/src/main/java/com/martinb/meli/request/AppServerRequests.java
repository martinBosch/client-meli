package com.martinb.meli.request;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppServerRequests {

    @GET("/")
    Call<String> helloWord();
}
