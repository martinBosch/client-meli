package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.QuestionPublishCallBack;
import com.martinb.meli.network.object_request.Question;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionViewModel extends ViewModel {

    private QuestionPublishCallBack callback;

    public LiveData<String> publishQuestion(String token, String productId, String question) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Void> call = appserverRequests.publishQuestion("Bearer " + token,
                productId, new Question(question));
        callback = new QuestionPublishCallBack();
        call.enqueue(callback);
        return callback.getData();
    }

    public String getErrorMsj() {
        return callback.getErrorMsj();
    }
}
