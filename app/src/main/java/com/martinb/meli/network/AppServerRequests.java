package com.martinb.meli.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppServerRequests {

    @GET("/")
    Call<String> helloWord();

    @POST("/signup")
    Call<AuthenticationResponse> signup(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user);
}