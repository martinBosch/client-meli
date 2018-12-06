package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.activity.purchase.PurchaseShippingMethodsActivity;
import com.martinb.meli.adapter.ProductRecyclerViewAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.ProductItem;
import com.martinb.meli.network.callback.SearchCallback;
import com.martinb.meli.view_model.SearchViewModel;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class SearchActivity extends AppCompatActivity {

    private SearchViewModel searchViewModel;

    public static final String SEARCH_NAME = "name";
    public static final String SEARCH_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar18);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void search(View view) {
        EditText nameText = findViewById(R.id.name);
        String name = nameText.getText().toString();

        EditText descriptionText = findViewById(R.id.description);
        String description = descriptionText.getText().toString();

        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra(SEARCH_NAME, name);
        intent.putExtra(SEARCH_DESCRIPTION, description);
        startActivity(intent);
    }
}
