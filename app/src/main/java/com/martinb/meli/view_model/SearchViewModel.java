package com.martinb.meli.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.martinb.meli.model.ProductItem;
import com.martinb.meli.network.AppServerRequestFactory;
import com.martinb.meli.network.AppServerRequests;
import com.martinb.meli.network.callback.SearchCallback;

import java.util.ArrayList;

import retrofit2.Call;

public class SearchViewModel extends ViewModel {

    private SearchCallback callback;

    public LiveData<ArrayList<ProductItem>> search(String token, String name, String description) {
        AppServerRequests appserverRequests = AppServerRequestFactory.getInstance();
        Call<ArrayList<ProductItem>> call = appserverRequests.search("Berarer " + token, name, description);
        callback = new SearchCallback();
        call.enqueue(callback);
        return callback.getData();
    }

    public String getRefreshToken() {
        return callback.getRefreshToken();
    }

    public String getErrorMsj() {
        return callback.getErrorMsj();
    }
}
