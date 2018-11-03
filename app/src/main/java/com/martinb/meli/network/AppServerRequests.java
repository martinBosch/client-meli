package com.martinb.meli.network;

import com.martinb.meli.model.ProductItem;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.network.object_request.PublishRequest;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.network.object_response.UserId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppServerRequests {

    @GET("/")
    Call<String> helloWord();

    @POST("/users/signup")
    Call<UserId> signup(@Body User user);

    @POST("/users/login")
    Call<UserId> login(@Body User user);

    @POST("/products")
    Call<Void> publish(@Header("Authorization") String token, @Body PublishRequest product);

    @GET("/products")
    Call<ArrayList<ProductItem>> productsPublished(@Header("Authorization") String token);

    @GET("/products/{productId}")
    Call<Product> productDetail(@Header("Authorization") String token, @Path("productId") String productId);

    @GET("/users/{userId}")
    Call<User> profile(@Header("Authorization") String token, @Path("userId") String userId);
}
