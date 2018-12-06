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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.UserInfo;
import com.martinb.meli.view_model.ProfileEditViewModel;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.activity.ProfileActivity.USER_INFO;

public class ProfileEditActivity extends AppCompatActivity {

    private ProfileEditViewModel profileEditViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        this.profileEditViewModel = ViewModelProviders.of(this)
                .get(ProfileEditViewModel.class);

        Intent intent = getIntent();
        UserInfo userInfo = (UserInfo) intent.getSerializableExtra(USER_INFO);

        setupToolbar();
        setupUserInfo(userInfo);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_profile_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.save:
                saveProfile();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveProfile() {
        String token = AccountAuthenticator.getAuthToken(ProfileEditActivity.this);
        String userId = AccountAuthenticator.getUserId(ProfileEditActivity.this);
        UserInfo userInfo = getNewUserInfo();
        profileEditViewModel.saveChanges(token, userId, userInfo).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                if (token == null) {
                    String e = profileEditViewModel.getErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                AccountAuthenticator.updateAuthToken(ProfileEditActivity.this, token);
                goProfileScreen();
            }
        });
    }

    private void goProfileScreen() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private UserInfo getNewUserInfo() {
        EditText display_name_text = findViewById(R.id.display_name_edit);
        String display_name = display_name_text.getText().toString();

        TextView email_text = findViewById(R.id.email_edit);
        String email = email_text.getText().toString();

        EditText phone_text = findViewById(R.id.phone_edit);
        String phone = phone_text.getText().toString();

        return new UserInfo(display_name, email, phone);
    }

    private void setupUserInfo(UserInfo userInfo) {
        EditText display_name = findViewById(R.id.display_name_edit);
        display_name.setText(userInfo.getDisplayName());

        TextView email = findViewById(R.id.email_edit);
        email.setText(userInfo.getEmail());

        EditText phone = findViewById(R.id.phone_edit);
        phone.setText(userInfo.getPhone());
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }
}
