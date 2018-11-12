package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.PaymentRegisterCallBack;
import com.martinb.meli.network.callback.PurchaseRegisterCallBack;
import com.martinb.meli.network.object_request.Payment;
import com.martinb.meli.network.object_request.Unit;
import com.martinb.meli.network.object_response.PaymentId;
import com.martinb.meli.network.object_response.PurchaseId;

import retrofit2.Call;

public class PurchaseViewModel extends ViewModel {

    private PurchaseRegisterCallBack purchaseRegisterCallBack;
    private PaymentRegisterCallBack paymentRegisterCallBack;

    public LiveData<String> registerPurchase(String token, String productId, Integer units_purchased) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<PurchaseId> call = appserverRequests.registerPurchase("Bearer " + token,
                productId, new Unit(units_purchased));
        purchaseRegisterCallBack = new PurchaseRegisterCallBack();
        call.enqueue(purchaseRegisterCallBack);
        return purchaseRegisterCallBack.getData();
    }

    public String getRegisterPurchaseToken() {
        return purchaseRegisterCallBack.getRefreshToken();
    }

    public String getRegisterPurchaseErrorMsj() {
        return purchaseRegisterCallBack.getErrorMsj();
    }

    public LiveData<String> registerPayment(String token, String purchaseId, Payment payment) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<PaymentId> call = appserverRequests.registerPayment("Bearer " + token,
                purchaseId, payment);
        paymentRegisterCallBack = new PaymentRegisterCallBack();
        call.enqueue(paymentRegisterCallBack);
        return paymentRegisterCallBack.getData();
    }

    public String getRegisterPaymentToken() {
        return purchaseRegisterCallBack.getRefreshToken();
    }

    public String getRegisterPaymentErrorMsj() {
        return purchaseRegisterCallBack.getErrorMsj();
    }
}
