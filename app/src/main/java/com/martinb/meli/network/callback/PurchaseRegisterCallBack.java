package com.martinb.meli.network.callback;

import com.martinb.meli.network.object_request.Unit;
import com.martinb.meli.network.object_response.PurchaseId;

import retrofit2.Response;

public class PurchaseRegisterCallBack extends BaseCallBack<String, PurchaseId> {

    private String refreshToken;

    @Override
    protected void handleGoodRequest(Response<PurchaseId> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        PurchaseId purchaseId = response.body();
        this.data.setValue( purchaseId.getStr() );
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
