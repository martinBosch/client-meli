package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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
    private MutableLiveData<String> firebaseToken = new MutableLiveData<>();
    private String firebaseErrorMsj;

    public LiveData<UserId> signup(User user) {
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

    public LiveData<String> getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            firebaseToken.setValue(token);
                        } else {
                            firebaseToken.setValue(null);
                            firebaseErrorMsj = task.getException().getMessage();
                        }
                    }
                });
        return firebaseToken;
    }

    public String getFirebaseErrorMsj() {
        return this.firebaseErrorMsj;
    }
}
