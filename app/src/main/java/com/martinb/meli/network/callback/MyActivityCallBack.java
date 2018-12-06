package com.martinb.meli.network.callback;

import com.martinb.meli.network.object_response.MyActivity;

import java.util.ArrayList;

import retrofit2.Response;

public class MyActivityCallBack extends BaseCallBack<ArrayList<MyActivity>, ArrayList<MyActivity>> {

    private String refreshToken = null;

    @Override
    protected void handleGoodRequest(Response<ArrayList<MyActivity>> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        ArrayList<MyActivity> myActivities = response.body();
        this.data.setValue(myActivities);
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
