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
import com.martinb.meli.view_model.MySalesViewModel;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.activity.MyPurchasesActivity.CHAT_ID;

public class MySalesActivity extends AppCompatActivity {

    private MySalesViewModel mySalesViewModel;
    private RecyclerView mySalesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sales);

        mySalesViewModel = ViewModelProviders.of(this).get(MySalesViewModel.class);

        setupToolbar();
        setupMySales();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar17);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupMySales() {
        String token = AccountAuthenticator.getAuthToken(MySalesActivity.this);
        mySalesViewModel.mySales(token).observe(this, new Observer<ArrayList<MyActivity>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MyActivity> mySales) {
                if (mySales == null) {
                    String e = mySalesViewModel.getMyPurchasesErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = mySalesViewModel.getMyPurchasesToken();
//                    AccountAuthenticator.updateAuthToken(MyPurchasesActivity.this, token);
                _setupMySales(mySales);

            }
        });
    }

    private void _setupMySales(ArrayList<MyActivity> mySales) {
        mySalesList = findViewById(R.id.my_sales);
        mySalesList.setLayoutManager(new LinearLayoutManager(this));
        MyActivitiesAdapter adapter = new MyActivitiesAdapter(this, mySales);
        setupOnClick(adapter, mySales);
        mySalesList.setAdapter(adapter);
    }

    private void setupOnClick(MyActivitiesAdapter adapter, final ArrayList<MyActivity> mySales) {
        adapter.setOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                String _id = mySales.get(mySalesList.getChildAdapterPosition(view)).getActivityId();
                intent.putExtra(CHAT_ID, _id);
                view.getContext().startActivity(intent);
            }
        });
    }


    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
