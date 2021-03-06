package com.martinb.meli.network.callback;

import com.martinb.meli.network.object_request.Question;

import java.util.ArrayList;

import retrofit2.Response;

public class QuestionsCallBack extends BaseCallBack<ArrayList<Question>, ArrayList<Question>> {

    private String refreshToken = null;

    @Override
    protected void handleGoodRequest(Response<ArrayList<Question>> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        ArrayList<Question> questions = response.body();
        this.data.setValue(questions);
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
