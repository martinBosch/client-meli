package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.RegisterCallBack;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.network.object_response.UserId;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {

    private RegisterCallBack callBack;

    public LiveData<UserId> signup(String email, String password, String displayName, String phone) {
        User user = new User(email, password, displayName, phone);

        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<UserId> call = appserverRequests.signup(user);
        callBack = new RegisterCallBack();
        call.enqueue(callBack);
        return callBack.getData();
    }

    public String getRefreshToken() {
        return callBack.getRefreshToken();
    }

    public String getErrorMsj() {
        return callBack.getErrorMsj();
    }
}
