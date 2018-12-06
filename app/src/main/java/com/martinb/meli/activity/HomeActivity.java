package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.martinb.meli.R;
import com.martinb.meli.adapter.ProductRecyclerViewAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.ProductItem;
import com.martinb.meli.view_model.HomeViewModel;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;

    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        setupToolbar();
        setupNavigationDrawer();
        setupProductsGrid();
        setupFirebase();
    }

    private void setupFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                } else {
                    showErrorMessage(task.getException().getMessage());
                }
            }
        });
    }

    private void setupNavigationDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.home_main);
        NavigationView navView = (NavigationView) findViewById(R.id.nv);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId) {
                    case R.id.user:
                        goProfileScreen();
                        break;
                    case R.id.account:
                        goMyAccountScreen();
                        break;
                    case R.id.sell:
                        goPublischScreen();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void goPublischScreen() {
        Intent intent = new Intent(this, PublishProductActivity.class);
        startActivity(intent);
    }

    private void goProfileScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void goMyAccountScreen() {
        Intent intent = new Intent(this, MyAccountActivity.class);
        startActivity(intent);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.search:
                goSearchScreen();
                return true;
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupProductsGrid() {
        String token = AccountAuthenticator.getAuthToken(HomeActivity.this);
        homeViewModel.getPublishedProduct(token).observe(this, new Observer<ArrayList<ProductItem>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ProductItem> products) {
                if (products == null) {
                    String e = homeViewModel.getErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = homeViewModel.getRefreshToken();
                AccountAuthenticator.updateAuthToken(HomeActivity.this, token);
                _setupProductsGrid(products);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        });
    }

    private void _setupProductsGrid(ArrayList<ProductItem> products) {
        StaggeredGridLayoutManager recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, 1);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        ProductRecyclerViewAdapter recyclerAdapter = new ProductRecyclerViewAdapter(HomeActivity.this, products);
        recyclerView.setAdapter(recyclerAdapter);
    }

    // Si la navigation bar esta activada y se presiona hacia atras
    // cierro la misma, en vez de la activity
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void goSearchScreen() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
