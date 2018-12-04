package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.AnswerCallback;
import com.martinb.meli.network.object_request.Answer;

import retrofit2.Call;

public class AnswerViewModel extends ViewModel {

    private AnswerCallback callback;

    public LiveData<String> publishAnswer(String token, String questionId, String answer) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Void> call = appserverRequests.publishAnswer("Bearer " + token,
                questionId, new Answer(answer));
        callback = new AnswerCallback();
        call.enqueue(callback);
        return callback.getData();
    }

    public String getErrorMsj() {
        return callback.getErrorMsj();
    }
}
