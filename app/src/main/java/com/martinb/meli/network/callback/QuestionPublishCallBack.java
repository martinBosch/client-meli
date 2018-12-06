package com.martinb.meli.network.callback;

import retrofit2.Response;

public class QuestionPublishCallBack extends BaseCallBack<String, Void> {
    @Override
    protected void handleGoodRequest(Response<Void> response) {
        okhttp3.Headers headers = response.headers();
        String refreshToken = headers.get("Bearer");
        this.data.setValue(refreshToken);
    }
}
