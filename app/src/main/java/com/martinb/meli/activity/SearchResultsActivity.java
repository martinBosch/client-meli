package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.adapter.ProductRecyclerViewAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.ProductItem;
import com.martinb.meli.view_model.SearchViewModel;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.activity.SearchActivity.SEARCH_DESCRIPTION;
import static com.martinb.meli.activity.SearchActivity.SEARCH_NAME;

public class SearchResultsActivity extends AppCompatActivity {

    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        Intent intent = getIntent();
        String name = (String) intent.getStringExtra(SEARCH_NAME);
        String description = (String) intent.getStringExtra(SEARCH_DESCRIPTION);

        setupToolbar();
        setupSearchResults(name, description);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar19);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupSearchResults(String name, String description) {
        String token = AccountAuthenticator.getAuthToken(SearchResultsActivity.this);
        searchViewModel.search(token, name, description).observe(this, new Observer<ArrayList<ProductItem>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ProductItem> productItems) {
                if (productItems == null) {
                    String e = searchViewModel.getErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = searchViewModel.getRefreshToken();
                AccountAuthenticator.updateAuthToken(SearchResultsActivity.this, token);
                _setupSearchResults(productItems);
            }
        });
    }

    private void _setupSearchResults(ArrayList<ProductItem> results) {
        StaggeredGridLayoutManager recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, 1);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_results);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        ProductRecyclerViewAdapter recyclerAdapter = new ProductRecyclerViewAdapter(SearchResultsActivity.this, results);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
