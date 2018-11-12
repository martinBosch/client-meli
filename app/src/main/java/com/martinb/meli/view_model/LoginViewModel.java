package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.LoginCallBack;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.network.object_response.UserId;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private LoginCallBack callBack;

    public LiveData<UserId> login(String email, String password) {
        User user = new User(email, password, null, null);

        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<UserId> call = appserverRequests.login(user);
        callBack = new LoginCallBack();
        call.enqueue( callBack );
        return callBack.getData();
    }

    public String getRefreshToken() {
        return callBack.getRefreshToken();
    }

    public String getErrorMsj() {
        return callBack.getErrorMsj();
    }
}
