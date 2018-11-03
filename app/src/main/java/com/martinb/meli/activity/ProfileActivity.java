package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.view_model.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity {

    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        setupToolbar();
        setupUserProfile();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupUserProfile() {
        String token = AccountAuthenticator.getAuthToken(ProfileActivity.this);
        String userId = AccountAuthenticator.getUserId(ProfileActivity.this);
        profileViewModel.getUserInfo(token, userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
//                    AccountAuthenticator.updateAuthToken(ProductDetailsActivity.this, token);
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    _setupUserProfile(user);
                } else {
                    String e = profileViewModel.getErrorMsj();
                    showMessage(e);
                }
            }
        });
    }

    private void _setupUserProfile(User user) {
        TextView display_name = findViewById(R.id.display_name);
        display_name.setText(user.getDisplayName());

        TextView email = findViewById(R.id.email);
        email.setText(user.getEmail());

        TextView phone = findViewById(R.id.phone);
        phone.setText(user.getPhone());

        makeProfileVisible();
    }

    private void makeProfileVisible() {
        TextView display_name_title = findViewById(R.id.display_name_title);
        TextView display_name = findViewById(R.id.display_name);

        TextView email_title = findViewById(R.id.email_title);
        TextView email = findViewById(R.id.email);

        TextView phone_title = findViewById(R.id.phone_title);
        TextView phone = findViewById(R.id.phone);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        display_name_title.setVisibility(View.VISIBLE);
        display_name.setVisibility(View.VISIBLE);
        email_title.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        phone_title.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}
