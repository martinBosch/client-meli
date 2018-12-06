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
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.adapter.ProductViewHolders;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.view_model.QuestionViewModel;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.adapter.ProductViewHolders.ID_PRODUCTO;

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
                publish();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void publish() {
        String token = AccountAuthenticator.getAuthToken(QuestionActivity.this);

        Intent intent = getIntent();
        productId = intent.getStringExtra(ID_PRODUCTO);

        EditText question_text = findViewById(R.id.question);
        String question = question_text.getText().toString();

        this.questionViewModel.publishQuestion(token, productId, question).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                if (token == null) {
                    String e = questionViewModel.getErrorMsj();
                    showErrorMessage(e);
                    return;
                }
                AccountAuthenticator.updateAuthToken(QuestionActivity.this, token);
                showSuccessMessage(QUESTION_PUBLISHED);
                goProductDetailsScreen();
            }
        });
    }

    private void showSuccessMessage(String msj) {
        Toasty.success(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void goProductDetailsScreen() {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(ID_PRODUCTO, productId);
        startActivity(intent);
    }
}
