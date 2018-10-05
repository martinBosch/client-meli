package com.martinb.meli.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.model.Product;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        configureToolbar();
        configureNavigationDrawer();
        configureProductsGrid();
        }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavigationDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.home_main);
        NavigationView navView = (NavigationView) findViewById(R.id.nv);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                switch (itemId) {
                    case R.id.user:
                        Toast.makeText(HomeActivity.this, "User", Toast.LENGTH_SHORT).show();
                    case R.id.account:
                        Toast.makeText(HomeActivity.this, "Account", Toast.LENGTH_SHORT).show();
                    case R.id.sell:
                        Toast.makeText(HomeActivity.this, "Sell", Toast.LENGTH_SHORT).show();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.search:
                //Manejar search
                Toast.makeText(HomeActivity.this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                Toast.makeText(HomeActivity.this, "Navigation", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureProductsGrid() {
        StaggeredGridLayoutManager recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, 1);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        List<Product> products = getListItemData();

        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(HomeActivity.this, products);
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


    private List<Product> getListItemData(){
        List<Product> listViewItems = new ArrayList<Product>();

        Product product1 = new Product(R.drawable.one, "$10", "bbbbbbb cccccccccc ddddddd");
        Product product2 = new Product(R.drawable.two, "$20", "bbbbbbb cccccccccc");
        listViewItems.add(product1);
        listViewItems.add(product2);

        product1 = new Product(R.drawable.three, "$10", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        product2 = new Product(R.drawable.four, "$20", "bbbbbbb cccccccccc");
        listViewItems.add(product1);
        listViewItems.add(product2);

        product1 = new Product(R.drawable.one, "$10", "bbbbbbb cccccccccc");
        product2 = new Product(R.drawable.two, "$20", "bbbbbbb cccccccccc");
        listViewItems.add(product1);
        listViewItems.add(product2);

        product1 = new Product(R.drawable.three, "$10", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        product2 = new Product(R.drawable.four, "$20", "bbbbbbb cccccccccc");
        listViewItems.add(product1);
        listViewItems.add(product2);

        product1 = new Product(R.drawable.one, "$10", "bbbbbbb cccccccccc");
        product2 = new Product(R.drawable.two, "$20", "bbbbbbb cccccccccc");
        listViewItems.add(product1);
        listViewItems.add(product2);

        product1 = new Product(R.drawable.three, "$10", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        product2 = new Product(R.drawable.four, "$20", "bbbbbbb cccccccccc");
        listViewItems.add(product1);
        listViewItems.add(product2);

        return listViewItems;
    }
}