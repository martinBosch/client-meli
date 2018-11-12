package com.martinb.meli.network.callback;

import com.martinb.meli.network.object_response.UserId;

import retrofit2.Response;

public class LoginCallBack extends BaseCallBack<UserId, UserId> {

    private String refreshToken = null;

    @Override
    protected void handleGoodRequest(Response<UserId> response) {
        data.setValue( response.body() );

        okhttp3.Headers headers = response.headers();
        refreshToken = headers.get("Bearer");
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
