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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.martinb.meli.R;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.model.FacebookManager;
import com.martinb.meli.network.object_request.User;
import com.martinb.meli.network.object_response.UserId;
import com.martinb.meli.view_model.LoginViewModel;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginFbButton;
    private CallbackManager callbackManager;
    private LoginViewModel loginViewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    public void registerWithEmail(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loginWithFacebook(View view) {
        callbackManager = CallbackManager.Factory.create();
        loginFbButton = (LoginButton) findViewById(R.id.loginFbButton);
        loginFbButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        manageFacebookLogin();
    }

    private void manageFacebookLogin() {
        loginFbButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                FacebookManager fbManager = new FacebookManager();
                fbManager.requestProfileInfo(loginResult);
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginWithEmail(View view) {
        EditText editTextEmail = (EditText) findViewById(R.id.input_email);
        String email = editTextEmail.getText().toString();

        EditText editTextPassword = (EditText) findViewById(R.id.input_password);
        String password = editTextPassword.getText().toString();

        AccountAuthenticator.createAccount(this, email, password);
        this.user = new User(email, password, null, null);
        getFirebaseToken();
    }

    private void getFirebaseToken() {
        loginViewModel.getFirebaseToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String firebaseToken) {
                if (firebaseToken == null) {
                    String e = loginViewModel.getFirebaseErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                user.setRegistrationId(firebaseToken);
                _loginWithEmail();
            }
        });
    }

    private void _loginWithEmail() {
        loginViewModel.login(user).observe(this, new Observer<UserId>() {
            @Override
            public void onChanged(@Nullable UserId userId) {
                if (userId == null) {
                    String e = loginViewModel.getErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                String token = loginViewModel.getRefreshToken();
                AccountAuthenticator.setAuthToken(LoginActivity.this, token);
                AccountAuthenticator.setUserId(LoginActivity.this, userId.getStr());
                goMainScreen();
            }
        });
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
