package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.activity.purchase.PurchaseShippingMethodsActivity;
import com.martinb.meli.adapter.GalleryAdapter;
import com.martinb.meli.adapter.ProductViewHolders;
import com.martinb.meli.adapter.QuestionAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.Purchase;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.network.object_request.Question;
import com.martinb.meli.view_model.ProductDetailsViewModel;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.jar.Attributes;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.adapter.ProductViewHolders.ID_PRODUCTO;

public class ProductDetailsActivity extends AppCompatActivity {

    private ProductDetailsViewModel productDetailsViewModel;

    private String productId;
    private Product product;
    private Context context = this;

    public static final String PURCHASE = "purchase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        this.productDetailsViewModel = ViewModelProviders.of(this).get(ProductDetailsViewModel.class);

        Intent intent = getIntent();
        productId = intent.getStringExtra(ID_PRODUCTO);

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
                if (product == null) {
                    String e = productDetailsViewModel.getPublicDetailsErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = productDetailsViewModel.getPublicDetailsToken();
//                    AccountAuthenticator.updateAuthToken(ProductDetailsActivity.this, token);
                _setupProductDetails(product);
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
        info_seller.setText(product.getDisplay_name());

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

    private void setupQuestions(final String productId) {
        TextView question = findViewById(R.id.questions_title);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductQuestionsActivity.class);
                intent.putExtra(ID_PRODUCTO, productId);
                startActivity(intent);
            }
        });
    }

    public void comprar(View view) {
        Purchase purchase = new Purchase();
        purchase.setProductId(productId);
        purchase.setPrice(product.getPrice());
        Spinner amount = findViewById(R.id.cantidad);
        Integer amount_purchased = Integer.parseInt( amount.getSelectedItem().toString() );
        purchase.setUnits(amount_purchased);

        Intent intent = new Intent(this, PurchaseShippingMethodsActivity.class);
        intent.putExtra(PURCHASE, purchase);
        startActivity(intent);
    }

    public void ask(View view) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(ID_PRODUCTO, productId);
        startActivity(intent);
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
