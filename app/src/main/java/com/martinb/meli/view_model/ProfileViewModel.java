package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.ProfileCallback;
import com.martinb.meli.network.object_request.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private ProfileCallback callback;

    public LiveData<User> getUserInfo(String token, String userId) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<User> call = appserverRequests.profile("Bearer " + token, userId);
        callback = new ProfileCallback();
        call.enqueue( callback );
        return callback.getData();
    }

    public String getToken() {
        return callback.getRefreshToken();
    }

    public String getErrorMsj() {
        return callback.getErrorMsj();
    }
}
