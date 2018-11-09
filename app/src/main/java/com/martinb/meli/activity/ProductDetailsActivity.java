package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.activity.purchase.PurchaseShippingMethodsActivity;
import com.martinb.meli.adapter.GalleryAdapter;
import com.martinb.meli.adapter.ProductViewHolders;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.Purchase;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.view_model.ProductDetailsViewModel;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    private ProductDetailsViewModel productDetailsViewModel;

    private String productId;
    private Product product;

    public static final String PURCHASE = "purchase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        this.productDetailsViewModel = ViewModelProviders.of(this).get(ProductDetailsViewModel.class);

        Intent intent = getIntent();
        productId = intent.getStringExtra(ProductViewHolders.ID_PRODUCTO);

        setupToolbar();
        setupQuestions(productId);
        setupProductDetails(productId);
    }

    private void setupToolbar() {
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

    private void setupProductDetails(String productId) {
        String token = AccountAuthenticator.getAuthToken(ProductDetailsActivity.this);
        productDetailsViewModel.getProductDetails(token, productId).observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {
                if (product != null) {
                    String token = productDetailsViewModel.getToken();
//                    AccountAuthenticator.updateAuthToken(ProductDetailsActivity.this, token);
                    _setupProductDetails(product);
                } else {
                    String e = productDetailsViewModel.getErrorMsj();
                    showMessage(e);
                }
            }
        });
    }

    private void _setupProductDetails(Product product) {
        this.product = product;

        TextView titulo = findViewById(R.id.title);
        titulo.setText(product.getName());

        TextView price = findViewById(R.id.price);
        price.setText( String.format("$ %s", product.getPrice()) );

        TextView description = findViewById(R.id.description);
        description.setText(product.getDescription());

        setupGallery(product.getImages());

        TextView category = findViewById(R.id.category);
        category.setText(product.getCategory());

        setupUnits(product.getUnits());

        TextView info_seller = findViewById(R.id.info_seller);
        info_seller.setText("Martin Bosch");

        TextView ubication_seller = findViewById(R.id.ubication_seller);
        ubication_seller.setText(product.getUbication());

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        findViewById(R.id.product_details).setVisibility(View.VISIBLE);
    }

    private void setupGallery(ArrayList<Bitmap> images) {
        ViewPager viewPager = findViewById(R.id.gallery);
        GalleryAdapter adapter = new GalleryAdapter(ProductDetailsActivity.this, images);
        viewPager.setAdapter(adapter);
    }

    private void setupUnits(int units) {
        Spinner cantidad = findViewById(R.id.cantidad);
        ArrayList<Integer> items = new ArrayList<>();
        for (int i=1; i<=units; i++) {
            items.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, items);
        cantidad.setAdapter(adapter);
    }

    private void setupQuestions(String productId) {
        String token = AccountAuthenticator.getAuthToken(ProductDetailsActivity.this);
        productDetailsViewModel.questions(token, productId).observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> questions) {
                if (questions != null) {
                    String token = productDetailsViewModel.getToken();
//                    AccountAuthenticator.updateAuthToken(ProductDetailsActivity.this, token);
                    _setupQuestions(questions);
                } else {
                    String e = productDetailsViewModel.getErrorMsj();
                    showMessage(e);
                }
            }
        });
    }

    private void _setupQuestions(ArrayList<String> questions) {
        LinearLayout questions_layout = findViewById(R.id.questions);
        for(String question : questions) {
            TextView question_text = new TextView(this);
            question_text.setPadding(0,10,0,0);
            question_text.setText(question);
            questions_layout.addView( question_text );
        }
    }

    public void comprar(View view) {
        //Todo: deberia pasar un objeto que comtenga toda la info de una compra y se vaya completando
        // a medida que avanzan las pantallas.
        Purchase purchase = new Purchase();
        purchase.setProductId(productId);
        purchase.setPrice(product.getPrice());
        Spinner amount = findViewById(R.id.cantidad);
        Integer amount_purchased = Integer.parseInt( amount.getSelectedItem().toString() );
        purchase.setAmount(amount_purchased);

        Intent intent = new Intent(this, PurchaseShippingMethodsActivity.class);
        intent.putExtra(PURCHASE, purchase);
        startActivity(intent);
    }

    public void ask(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(ProductViewHolders.ID_PRODUCTO, productId);
        startActivity(intent);
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}
