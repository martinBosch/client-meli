package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.model.UserInfo;
import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.ProfileEditCallBack;

import retrofit2.Call;

public class ProfileEditViewModel extends ViewModel {

    private ProfileEditCallBack callback;

    public LiveData<String> saveChanges(String token, String userId, UserInfo userInfo) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Void> call = appserverRequests.editProfile("Bearer " + token, userId, userInfo);
        callback = new ProfileEditCallBack();
        call.enqueue(callback);
        return callback.getData();
    }

    public String getErrorMsj() {
        return callback.getErrorMsj();
    }
}
