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
import com.martinb.meli.network.AuthenticationResponse;
import com.martinb.meli.network.User;
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

        EditText editTextPassword = (EditText) findViewById(R.id.input_password);
        String password = editTextPassword.getText().toString();

        signup(email, password);
    }

    private void signup(String email, String password) {
        registerViewModel.signup(email, password).observe(this, new Observer<AuthenticationResponse>() {
            @Override
            public void onChanged(@Nullable AuthenticationResponse authenticationResponse) {
                if (authenticationResponse.isSuccessful()) {
                    User user = authenticationResponse.getUser();
                    AccountAuthenticator.createAccount(RegisterActivity.this,
                            user.getEmail(), user.getPassword(), user.getToken());
                    goMainScreen();
                } else {
                    String e = authenticationResponse.getErrorMessage();
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
