package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.adapter.ProductViewHolders;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.view_model.QuestionViewModel;

public class QuestionActivity extends AppCompatActivity {

    private QuestionViewModel questionViewModel;
    private String productId;

    private static final String QUESTION_PUBLISHED = "Tu pregunta fue publicada!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.send:
                send();
//                Toast.makeText(QuestionActivity.this, "Send", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void send() {
        String token = AccountAuthenticator.getAuthToken(QuestionActivity.this);

        Intent intent = getIntent();
        productId = intent.getStringExtra(ProductViewHolders.ID_PRODUCTO);

        EditText question_text = findViewById(R.id.question);
        String question = question_text.getText().toString();

        this.questionViewModel.publishQuestion(token, productId, question).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                if (token != null) {
//                    AccountAuthenticator.updateAuthToken(ProductDetailsActivity.this, token);
                    showMessage(QUESTION_PUBLISHED);
                    goProductDetailsScreen();
                } else {
                    String e = questionViewModel.getErrorMsj();
                    showMessage(e);
                }
            }
        });
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    private void goProductDetailsScreen() {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(ProductViewHolders.ID_PRODUCTO, productId);
        startActivity(intent);
    }
}
