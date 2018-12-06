package com.martinb.meli.activity.purchase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.martinb.meli.R;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.Purchase;
import com.martinb.meli.view_model.DeliveryViewModel;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.activity.ProductDetailsActivity.PURCHASE;

public class PurchaseDeliveryHomeActivity extends AppCompatActivity {

    Purchase purchase;
    private DeliveryViewModel deliveryViewModel;

    private String ubication;
    private Double latitude;
    private Double longitude;
    private Float cost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_delivery_home);

        Intent intent = getIntent();
        purchase = (Purchase) intent.getSerializableExtra(PURCHASE);

        this.deliveryViewModel = ViewModelProviders.of(this).get(DeliveryViewModel.class);

        setupToolbar();
        setupDeliveryDestination();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupDeliveryDestination() {
        PlaceAutocompleteFragment placeAutocomplete = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.delivery_direccion);

        placeAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                ubication = place.getAddress().toString();
                LatLng location = place.getLatLng();
                latitude = location.latitude;
                longitude = location.longitude;
                estimateDeliveryCost();
            }

            @Override
            public void onError(Status status) {
                showErrorMessage(status.getStatusMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void estimateDeliveryCost() {
        String token = AccountAuthenticator.getAuthToken(this);
        deliveryViewModel.estimateDeliveryCost(token, purchase.getProductId(),
                ubication, latitude, longitude).observe(this, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float delivery_cost) {
                if (delivery_cost == null) {
                    String e = deliveryViewModel.getDeliveryErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = deliveryViewModel.getDeliveryToken();
//                    AccountAuthenticator.updateAuthToken(ProductDetailsActivity.this, token);
                cost = delivery_cost;
            }
        });
    }

    public void continueToPayment(View view) {
        if (this.cost == null) {
            showInfoMessage("Aguarde. Se esta calculando el costo de envío...");
            return;
        }
        purchase.setDestination_address(ubication);
        purchase.setDestination_latitude(latitude);
        purchase.setDestination_longitude(longitude);
        purchase.setDelivery_cost(cost);

        Intent i = new Intent(this, PurchasePaymentMethodsActivity.class);
        i.putExtra(PURCHASE, purchase);
        startActivity(i);
    }

    private void showInfoMessage(String msj) {
        Toasty.info(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
