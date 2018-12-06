package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.DeliveryEstimateCallBack;
import com.martinb.meli.network.object_response.DeliveryCost;

import retrofit2.Call;

public class DeliveryViewModel extends ViewModel {

    private DeliveryEstimateCallBack deliveryEstimateCallBack;

    public LiveData<Float> estimateDeliveryCost(String token, String productId, String adress,
                                                Double latitude, Double longitude) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<DeliveryCost> call = appserverRequests.estimateDeliveryCost("Bearer " + token,
                productId, adress, latitude, longitude);
        deliveryEstimateCallBack = new DeliveryEstimateCallBack();
        call.enqueue(deliveryEstimateCallBack);
        return deliveryEstimateCallBack.getData();
    }

    public String getDeliveryToken() {
        return deliveryEstimateCallBack.getRefreshToken();
    }

    public String getDeliveryErrorMsj() {
        return deliveryEstimateCallBack.getErrorMsj();
    }
}
