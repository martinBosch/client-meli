package com.martinb.meli.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        setupToolbar();
        setupAccountItems();

    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
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

    private void setupAccountItems() {
        LinearLayout my_shopping = findViewById(R.id.my_shopping_option);
        my_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goChatScreen();
//                Toast.makeText(MyAccountActivity.this, "Mis compras", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout my_sells = findViewById(R.id.my_sell_option);
        my_sells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyAccountActivity.this, "Mis ventas", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout my_data = findViewById(R.id.my_data_option);
        my_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goProfileScreen();
            }
        });
    }

    private void goProfileScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void goChatScreen() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}
