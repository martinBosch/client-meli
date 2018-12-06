package com.martinb.meli.activity.purchase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.activity.HomeActivity;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.Purchase;
import com.martinb.meli.view_model.PurchaseViewModel;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.activity.ProductDetailsActivity.PURCHASE;

public class PurchaseResumeActivity extends AppCompatActivity {

    private static final String PURCHASE_CONFIRM_MSG = "Tu compra se realizo con exito";

    private PurchaseViewModel purchaseViewModel;
    private Purchase purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_resume);

        this.purchaseViewModel = ViewModelProviders.of(this).get(PurchaseViewModel.class);

        Intent intent = getIntent();
        purchase = (Purchase) intent.getSerializableExtra(PURCHASE);

        setupToolbar();
        setupResumePayment();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupResumePayment() {
        TextView product = findViewById(R.id.product);
        Float price = purchase.getPrice();
        product.setText( String.format("%s", price) );

        TextView amount_text = findViewById(R.id.units);
        Integer amount = purchase.getUnits();
        amount_text.setText( String.format("%s", amount) );

        TextView subtotal_text = findViewById(R.id.subtotal);
        Float subtotal = amount * price;
        subtotal_text.setText( String.format("%s", subtotal) );

        TextView deliveryCost_text = findViewById(R.id.delivery_cost);
        Float deliveryCost = purchase.getDelivery_cost();
        deliveryCost_text.setText( String.format("%s", deliveryCost) );

        TextView total_text = findViewById(R.id.total);
        Float total = subtotal + deliveryCost;
        total_text.setText( String.format("%s", total) );
    }

    public void confirm_purchase(View view) {
        String token = AccountAuthenticator.getAuthToken(PurchaseResumeActivity.this);
        purchaseViewModel.registerPurchase(token, purchase.getProductId(), purchase.getUnits()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String purchaseId) {
                if (purchaseId == null) {
                    String e = purchaseViewModel.getPurchaseErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = purchaseViewModel.getPurchaseToken();
//                AccountAuthenticator.updateAuthToken(PurchaseResumeActivity.this, token);
                if (purchase.isDelivery()) {
                    register_delivery(token, purchaseId);
                } else {
                    register_payment(token, purchaseId);
                }
            }
        });
    }

    private void register_delivery(String token, final String purchaseId) {
        purchaseViewModel.registerDelivery(token, purchaseId, purchase.getUbication()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                if (token == null) {
                    String e = purchaseViewModel.getDeliveryErrorMsj();
                    showErrorMessage(e);
                    return;
                }
//                AccountAuthenticator.updateAuthToken(PurchaseResumeActivity.this, token);
                register_payment(token, purchaseId);
            }
        });
    }

    private void register_payment(String token, final String purchaseId) {
        purchaseViewModel.registerPayment(token, purchaseId, purchase.getPayment()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String paymentId) {
                if (paymentId == null) {
                    String e = purchaseViewModel.getPaymentErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = purchaseViewModel.getPaymentToken();
//                AccountAuthenticator.updateAuthToken(PurchaseResumeActivity.this, token);
                showSuccessMessage(PURCHASE_CONFIRM_MSG);
                goHomeScreen();
            }
        });
    }

    private void showSuccessMessage(String msj) {
        Toasty.success(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void goHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
