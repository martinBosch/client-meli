package com.martinb.meli.network.callback;

import android.arch.lifecycle.MutableLiveData;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// D : es el tipo del objeto que sera devuelto a la activity que llamo al ViewModel
// R : es el tipo del objeto que es devuelto por la request hecha en el ViewModel
public abstract class BaseCallBack<D, R> implements Callback<R> {

    private static final String ERROR_MSJ = "description";

    protected MutableLiveData<D> data = new MutableLiveData<>();
    private String errorMsj = null;

    @Override
    public void onResponse(Call<R> call, Response<R> response) {
        if (response.isSuccessful()) {
            handleGoodRequest(response);
        } else {
            handleBadRequest(response);
        }
    }

    @Override
    public void onFailure(Call<R> call, Throwable t) {
        data.setValue(null);
        errorMsj = t.getMessage();
    }

    protected abstract void handleGoodRequest(Response<R> response);

    private void handleBadRequest(Response<R> response) {
        data.setValue(null);
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

    public MutableLiveData<D> getData() {
        return data;
    }
}
