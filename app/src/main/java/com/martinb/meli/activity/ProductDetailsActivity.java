package com.martinb.meli.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.martinb.meli.R;
import com.martinb.meli.adapter.GalleryAdapter;
import com.martinb.meli.adapter.ProductViewHolders;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        configureToolbar();

        Intent intent = getIntent();
        String idProd = intent.getStringExtra(ProductViewHolders.ID_PRODUCTO);

        configureProduct();
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void configureProduct() {
        configureGallery();

        TextView titulo = findViewById(R.id.title);
        titulo.setText("aaaaaaaaaaaaaaaaaaa");

        TextView price = findViewById(R.id.price);
        price.setText("$20");

        TextView description = findViewById(R.id.description);
        description.setText("bla bla bla blaaaaaa blablabla");

        TextView category = findViewById(R.id.category);
        category.setText("electrodomestico");

        configureCantidad();

        TextView info_seller = findViewById(R.id.info_seller);
        info_seller.setText("Martin Bosch");

        TextView ubication_seller = findViewById(R.id.ubication_seller);
        info_seller.setText("Lanus, Buenos Aires");
    }

    private void configureGallery() {
        int[] images = {R.drawable.naruto, R.drawable.naruto, R.drawable.one, R.drawable.two};

        ViewPager viewPager = findViewById(R.id.gallery);
        GalleryAdapter adapter = new GalleryAdapter(ProductDetailsActivity.this, images);
        viewPager.setAdapter(adapter);
    }

    private void configureCantidad() {
        Spinner cantidad = findViewById(R.id.cantidad);
        int cant_disponible = 7;
        ArrayList<Integer> items = new ArrayList<>();
        for (int i=1; i<=cant_disponible; i++) {
            items.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, items);
        cantidad.setAdapter(adapter);
    }

    public void comprar(View view) {
        Toast.makeText(this, "Comprar", Toast.LENGTH_SHORT).show();
    }
}
