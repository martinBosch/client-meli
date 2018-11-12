package com.martinb.meli.network.callback;

import com.martinb.meli.network.object_response.PaymentId;

import retrofit2.Response;

public class PaymentRegisterCallBack extends BaseCallBack<String, PaymentId> {

    private String refreshToken;

    @Override
    protected void handleGoodRequest(Response<PaymentId> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        PaymentId paymentId = response.body();
        this.data.setValue( paymentId.getStr() );
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
