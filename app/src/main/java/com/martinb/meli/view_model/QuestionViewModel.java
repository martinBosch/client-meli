package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.object_request.Question;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionViewModel extends ViewModel {

    private static final String ERROR_MSJ = "description";

    private MutableLiveData<String> refreshToken = new MutableLiveData<>();
    private String errorMsj = null;

    public LiveData<String> publishQuestion(String token, String productId, String question) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<Void> call = appserverRequests.publishQuestion("Bearer " + token, productId, new Question(question));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    handleGoodRequest(response);
                } else {
                    handleBadRequest(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                refreshToken.setValue(null);
                errorMsj = t.getMessage();
            }
        });
        return refreshToken;
    }

    private void handleGoodRequest(Response<Void> response) {
        okhttp3.Headers headers = response.headers();
        refreshToken.setValue( headers.get("Bearer") );
    }

    private void handleBadRequest(Response<Void> response) {
        refreshToken.setValue(null);
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            errorMsj = jObjError.getString(ERROR_MSJ);
        } catch (Exception e) {
            errorMsj = e.getMessage();
        }
    }

    public String getErrorMsj() {
        return errorMsj;
    }
}
