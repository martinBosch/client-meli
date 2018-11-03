package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.view_model.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    public void register(View view) {
        EditText editTextName = (EditText) findViewById(R.id.input_first_name);
        String name = editTextName.getText().toString();

        EditText editTextLastName = (EditText) findViewById(R.id.input_last_name);
        String lastName = editTextLastName.getText().toString();

        EditText editTextEmail = (EditText) findViewById(R.id.input_email);
        String email = editTextEmail.getText().toString();

        EditText editTextPhone = (EditText) findViewById(R.id.input_phone);
        String phone = editTextPhone.getText().toString();

        EditText editTextPassword = (EditText) findViewById(R.id.input_password);
        String password = editTextPassword.getText().toString();

        AccountAuthenticator.createAccount(this, email, password);
        signup(email, password, name + " " + lastName, phone);
    }

    private void signup(String email, String password, String displayName, String phone) {
        registerViewModel.signup(email, password, displayName, phone).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                if (token != null) {
                    AccountAuthenticator.setAuthToken(RegisterActivity.this, token);
                    String userId = registerViewModel.getUserId();
                    AccountAuthenticator.setUserId(RegisterActivity.this, userId);
                    goMainScreen();
                } else {
                    String e = registerViewModel.getErrorMsj();
                    showErrorMessage(e);
                }
            }
        });
    }

    private void showErrorMessage(String e) {
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }

    public void goMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
