package com.martinb.meli.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.martinb.meli.R;
import com.martinb.meli.authentication.AccountAuthenticator;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean logged = userIsLoggedin();
        if (!logged) {
            goLoginScreen();
        } else {
            goHomeScreen();
        }
    }

    private boolean userIsLoggedin() {
        boolean loginFacebook = AccessToken.getCurrentAccessToken()!=null;
        boolean loginApp = AccountAuthenticator.getAuthToken(this)!=null;
        return loginFacebook || loginApp;
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view) {
        AccountAuthenticator.logOut(this);
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
}
