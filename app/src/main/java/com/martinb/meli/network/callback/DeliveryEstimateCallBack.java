package com.martinb.meli.network.callback;

import com.martinb.meli.network.object_response.DeliveryCost;

import retrofit2.Response;

public class DeliveryEstimateCallBack extends BaseCallBack<Float, DeliveryCost> {

    private String refreshToken = null;

    @Override
    protected void handleGoodRequest(Response<DeliveryCost> response) {
        okhttp3.Headers headers = response.headers();
        this.refreshToken = headers.get("Bearer");

        DeliveryCost deliveryCost = response.body();
        this.data.setValue(deliveryCost.getCost());
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
