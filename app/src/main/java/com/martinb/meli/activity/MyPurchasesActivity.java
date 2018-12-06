package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.adapter.MyActivitiesAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.network.object_response.MyActivity;
import com.martinb.meli.view_model.MyPurchasesViewModel;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MyPurchasesActivity extends AppCompatActivity {

    private MyPurchasesViewModel myPurchasesViewModel;
    public static final String CHAT_ID = "chat_id";

    private RecyclerView myPurchasesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purchases);

        myPurchasesViewModel = ViewModelProviders.of(this).get(MyPurchasesViewModel.class);

        setupToolbar();
        setupMyPurchases();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar16);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupMyPurchases() {
        String token = AccountAuthenticator.getAuthToken(MyPurchasesActivity.this);
        myPurchasesViewModel.myPurchases(token).observe(this, new Observer<ArrayList<MyActivity>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MyActivity> myPurchases) {
                if (myPurchases == null) {
                    String e = myPurchasesViewModel.getMyPurchasesErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = myPurchasesViewModel.getMyPurchasesToken();
                AccountAuthenticator.updateAuthToken(MyPurchasesActivity.this, token);
                _setupMyPurchases(myPurchases);
            }
        });
    }

    private void _setupMyPurchases(ArrayList<MyActivity> myPurchases) {
        myPurchasesList = findViewById(R.id.my_purchases);
        myPurchasesList.setLayoutManager(new LinearLayoutManager(this));
        MyActivitiesAdapter adapter = new MyActivitiesAdapter(this, myPurchases);
        setupOnClick(adapter, myPurchases);
        myPurchasesList.setAdapter(adapter);
    }

    private void setupOnClick(MyActivitiesAdapter adapter, final ArrayList<MyActivity> myPurchases) {
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                String _id = myPurchases.get(myPurchasesList.getChildAdapterPosition(view)).getActivityId();
                intent.putExtra(CHAT_ID, _id);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
