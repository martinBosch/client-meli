package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.martinb.meli.R;
import com.martinb.meli.adapter.GalleryPublishAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.ImageManager;
import com.martinb.meli.network.object_request.Product;
import com.martinb.meli.view_model.PublishProductViewModel;

import java.util.ArrayList;

public class PublishProductActivity extends AppCompatActivity {

    private static final int RESULT_UPLOAD_IMAGE = 1;
    private static final String SUCCESSFUL_PUBLICATION = "Producto publicado";


    private PublishProductViewModel publishProductViewModel;

    private String name;
    private String description;
    private ArrayList<String> encoded_images = new ArrayList<>();
    private float price;
    private String category;
    private int units;
    private String ubication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_product);

        publishProductViewModel = ViewModelProviders.of(this).get(PublishProductViewModel.class);

        setupToolbar();
        setupCategory();
        setupUbication();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Producto");
        categories.add("Vehiculo");
        categories.add("Inmuebles");
        categories.add("Servicio");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, categories);
        Spinner category = findViewById(R.id.category);
        category.setAdapter(adapter);
    }

    private void setupUbication() {
        PlaceAutocompleteFragment placeAutocomplete = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        placeAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                ubication = place.getAddress().toString();
            }

            @Override
            public void onError(Status status) {
                showMessage(status.getStatusMessage());
            }
        });
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        //Todo: guardar los items selecionados en una lista.

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.credit_card:
                if (checked) {}
                else
                break;
            case R.id.debit_card:
                if (checked) {}
                else
                break;
            case R.id.cash:
                if (checked) {}
                else
                break;
        }
    }

    public void uploadPhoto(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(galleryIntent, RESULT_UPLOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_UPLOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            setupPublishGallery(data);
        }
    }

    private void setupPublishGallery(@Nullable Intent data) {
        ArrayList<Uri> images = ImageManager.getSelectedImages(data);
        this.encoded_images = ImageManager.getEncodedImages(images, this.getContentResolver());

        ViewPager viewPager = findViewById(R.id.gallery_publish);
        GalleryPublishAdapter adapter = new GalleryPublishAdapter(PublishProductActivity.this, images);
        viewPager.setAdapter(adapter);
    }

    public void publish(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        this.name = name.getText().toString();

        EditText description = (EditText) findViewById(R.id.description);
        this.description = description.getText().toString();

        EditText price = (EditText) findViewById(R.id.price);
        this.price = Float.parseFloat(price.getText().toString());

        Spinner categories = findViewById(R.id.category);
        this.category = categories.getSelectedItem().toString();

        EditText units = findViewById(R.id.units);
        this.units = Integer.parseInt(units.getText().toString());

        String token = AccountAuthenticator.getAuthToken(PublishProductActivity.this);

        publishProductViewModel.publish(token, this.name, this.description, this.encoded_images,
                                        this.price, this.category, this.ubication, this.units)
                .observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                if (token != null) {
                    showMessage(SUCCESSFUL_PUBLICATION);
//                    AccountAuthenticator.updateAuthToken(PublishProductActivity.this, token);
                    goMainScreen();
                } else {
                    String e = publishProductViewModel.getErrorMsj();
                    showMessage(e);
                }
            }
        });
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    public void goMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
