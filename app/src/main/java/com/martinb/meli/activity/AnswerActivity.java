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
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.view_model.AnswerViewModel;

import es.dmoral.toasty.Toasty;

import static com.martinb.meli.activity.ProductQuestionsActivity.ID_QUESTION;
import static com.martinb.meli.adapter.ProductViewHolders.ID_PRODUCTO;

public class AnswerActivity extends AppCompatActivity {

    private AnswerViewModel answerViewModel;
    private String questionId;
    private String productId;

    private static final String ANSWER_PUBLISHED = "Tu respuesta fue publicada!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        this.answerViewModel = ViewModelProviders.of(this).get(AnswerViewModel.class);

        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar13);
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
        String token = AccountAuthenticator.getAuthToken(AnswerActivity.this);

        Intent intent = getIntent();
        questionId = intent.getStringExtra(ID_QUESTION);

        EditText answer_text = findViewById(R.id.answer);
        String answer = answer_text.getText().toString();

        this.answerViewModel.publishAnswer(token, questionId, answer).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String token) {
                if (token == null) {
                    String e = answerViewModel.getErrorMsj();
                    showErrorMessage(e);
                    return;
                }
//                AccountAuthenticator.updateAuthToken(AnswerActivity.this, token);
                showSuccessMessage(ANSWER_PUBLISHED);
                goProductQuestionsScreen();
            }
        });
    }

    private void showErrorMessage(String msj) {
        Toasty.error(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void showSuccessMessage(String msj) {
        Toasty.success(this, msj, Toast.LENGTH_SHORT, true).show();
    }

    private void goProductQuestionsScreen() {
        Intent intent = getIntent();
        productId = intent.getStringExtra(ID_PRODUCTO);

        Intent i = new Intent(this, ProductQuestionsActivity.class);
        i.putExtra(ID_PRODUCTO, productId);
        startActivity(i);
    }

}
