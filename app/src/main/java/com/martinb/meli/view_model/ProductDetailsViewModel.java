package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.martinb.meli.model.ImageManager;
import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.ProductDetailsCallBack;
import com.martinb.meli.network.callback.QuestionsCallBack;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.network.object_request.Question;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsViewModel extends ViewModel {

    private QuestionsCallBack questionsCallBack;
    private ProductDetailsCallBack productDetailsCallBack;

    public LiveData<ArrayList<String>> questions(String token, String productId) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<ArrayList<Question>> call = appserverRequests.questions("Bearer " + token, productId);
        questionsCallBack = new QuestionsCallBack();
        call.enqueue(  questionsCallBack );
        return questionsCallBack.getData();
    }

    public String getQuestionToken() {
        return questionsCallBack.getRefreshToken();
    }

    public String getQuestionErrorMsj() {
        return questionsCallBack.getErrorMsj();
    }


    public LiveData<Product> getProductDetails(String token, String productId) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Product> call = appserverRequests.productDetail("Bearer " + token, productId);
        productDetailsCallBack = new ProductDetailsCallBack();
        call.enqueue( productDetailsCallBack );
        return productDetailsCallBack.getData();
    }

    public String getPublicDetailsToken() {
        return productDetailsCallBack.getRefreshToken();
    }

    public String getPublicDetailsErrorMsj() {
        return productDetailsCallBack.getErrorMsj();
    }
}
