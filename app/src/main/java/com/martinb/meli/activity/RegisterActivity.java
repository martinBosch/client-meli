package com.martinb.meli.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.martinb.meli.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
    }
}
