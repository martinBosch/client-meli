package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.QuestionsCallBack;
import com.martinb.meli.network.object_request.Question;

import java.util.ArrayList;

import retrofit2.Call;

public class ProductQuestionViewModel extends ViewModel {

    private QuestionsCallBack questionsCallBack;

    public LiveData<ArrayList<Question>> questions(String token, String productId) {
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
}
