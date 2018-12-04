package com.martinb.meli.network;

import com.martinb.meli.model.ProductItem;
import com.martinb.meli.model.UserInfo;
import com.martinb.meli.network.object_request.Answer;
import com.martinb.meli.network.object_request.Payment;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.network.object_request.PublishRequest;
import com.martinb.meli.network.object_request.Question;
import com.martinb.meli.network.object_request.Unit;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.network.object_response.PaymentId;
import com.martinb.meli.network.object_response.PurchaseId;
import com.martinb.meli.network.object_response.UserId;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("/users/{userId}")
    Call<Void> editProfile(@Header("Authorization") String token, @Path("userId") String userId, @Body UserInfo userInfo);

    @POST("/products/{productId}/questions")
    Call<Void> publishQuestion(@Header("Authorization") String token, @Path("productId") String productId,  @Body Question question);

    @POST("/questions/{questionId}/answers")
    Call<Void> publishAnswer(@Header("Authorization") String token, @Path("questionId") String questionId,  @Body Answer answer);

    @GET("/products/{productId}/questions")
    Call<ArrayList<Question>> questions(@Header("Authorization") String token, @Path("productId") String productId);

    @POST("/products/{productId}/purchases")
    Call<PurchaseId> registerPurchase(@Header("Authorization") String token, @Path("productId") String productId, @Body Unit units);

    @POST("/purchases/{purchaseId}/payments")
    Call<PaymentId> registerPayment(@Header("Authorization") String token, @Path("purchaseId") String purchaseId, @Body Payment payment);
}
