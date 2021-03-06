package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.UserInfo;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.view_model.ProfileViewModel;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {

    private ProfileViewModel profileViewModel;
    private User user;

    public static final String USER_INFO = "user_info";

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.edit_profile:
                goEditProfileScreen();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupUserProfile() {
        String token = AccountAuthenticator.getAuthToken(ProfileActivity.this);
        String userId = AccountAuthenticator.getUserId(ProfileActivity.this);
        profileViewModel.getUserInfo(token, userId).observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user == null) {
                    String e = profileViewModel.getErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = profileViewModel.getToken();
                AccountAuthenticator.updateAuthToken(ProfileActivity.this, token);
                _setupUserProfile(user);
            }
        });
    }

    private void _setupUserProfile(User user) {
        this.user = user;

        TextView display_name = findViewById(R.id.display_name);
        display_name.setText(user.getDisplayName());

        TextView email = findViewById(R.id.email);
        email.setText(user.getEmail());

        TextView phone = findViewById(R.id.phone);
        phone.setText(user.getPhone());

        makeProfileVisible();
    }

    private void makeProfileVisible() {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        findViewById(R.id.profile).setVisibility(View.VISIBLE);
    }

    private void goEditProfileScreen() {
        UserInfo userInfo = new UserInfo(user.getDisplayName(), user.getEmail(), user.getPhone());
        Intent intent = new Intent(this, ProfileEditActivity.class);
        intent.putExtra(USER_INFO, userInfo);
        startActivity(intent);
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
