package com.martinb.meli.network.callback;

import com.martinb.meli.network.object_request.User;

import retrofit2.Response;

public class ProfileCallback extends BaseCallBack<User, User> {

    private String refreshToken = null;

    @Override
    protected void handleGoodRequest(Response<User> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        data.setValue( response.body() );
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
