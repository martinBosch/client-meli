package com.martinb.meli.network.callback;

import retrofit2.Response;

public class ProductPublishCallBack extends BaseCallBack<String, Void> {
    @Override
    protected void handleGoodRequest(Response<Void> response) {
        okhttp3.Headers headers = response.headers();
        data.setValue( headers.get("Bearer") );
    }
}
