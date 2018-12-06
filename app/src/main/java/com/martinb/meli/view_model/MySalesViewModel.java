package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.MyActivityCallBack;
import com.martinb.meli.network.object_response.MyActivity;

import java.util.ArrayList;

import retrofit2.Call;

public class MySalesViewModel extends ViewModel {

    private MyActivityCallBack myActivityCallBack;

    public LiveData<ArrayList<MyActivity>> mySales(String token) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<ArrayList<MyActivity>> call = appserverRequests.mySales("Bearer " + token);
        myActivityCallBack = new MyActivityCallBack();
        call.enqueue(myActivityCallBack);
        return myActivityCallBack.getData();
    }

    public String getMyPurchasesToken() {
        return myActivityCallBack.getRefreshToken();
    }

    public String getMyPurchasesErrorMsj() {
        return myActivityCallBack.getErrorMsj();
    }

}
