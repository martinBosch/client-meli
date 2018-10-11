package com.martinb.meli.network;

import com.martinb.meli.network.object_request.PublishRequest;
import com.martinb.meli.network.object_response.ProductDetailResponse;
import com.martinb.meli.network.object_response.ProductPublishedResponse;
import com.martinb.meli.network.object_response.PublishResponse;
import com.martinb.meli.network.object_response.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppServerRequests {

    @GET("/")
    Call<String> helloWord();

    @POST("/signup")
    Call<User> signup(@Body User user);

    @POST("/login")
    Call<User> login(@Body User user);

    @POST("/products")
    Call<PublishResponse> publish(@Header("Authorization") String token, @Body PublishRequest product);

    @GET("/products")
    Call<ProductPublishedResponse> productsPublished(@Header("Authorization") String token);

    @GET("/product/{productId}")
    Call<ProductDetailResponse> productDetail(@Header("Authorization") String token, @Path("productId") String productId);
}
