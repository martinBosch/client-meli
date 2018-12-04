package com.martinb.meli.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.martinb.meli.R;
import com.martinb.meli.adapter.ProductViewHolders;
import com.martinb.meli.adapter.QuestionAdapter;
import com.martinb.meli.authentication.AccountAuthenticator;
import com.martinb.meli.network.object_request.Question;
import com.martinb.meli.view_model.ProductQuestionViewModel;

import java.util.ArrayList;

import static com.martinb.meli.adapter.ProductViewHolders.ID_PRODUCTO;

public class ProductQuestionsActivity extends AppCompatActivity {

    public static final String ID_QUESTION = "id_question";

    private ProductQuestionViewModel productQuestionViewModel;
    private String productId;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_questions);

        productQuestionViewModel = ViewModelProviders.of(this).get(ProductQuestionViewModel.class);

        Intent intent = getIntent();
        productId = intent.getStringExtra(ProductViewHolders.ID_PRODUCTO);

        setupToolbar();
        setupQuestions(productId);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar14);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupQuestions(String productId) {
        String token = AccountAuthenticator.getAuthToken(ProductQuestionsActivity.this);
        productQuestionViewModel.questions(token, productId).observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Question> questions) {
                if (questions == null) {
                    String e = productQuestionViewModel.getQuestionErrorMsj();
                    showMessage(e);
                    return;
                }
                String token = productQuestionViewModel.getQuestionToken();
//                    AccountAuthenticator.updateAuthToken(ProductDetailsActivity.this, token);
                _setupQuestions(questions);
            }
        });
    }

    private void _setupQuestions(ArrayList<Question> questions) {
        ListView questions_list = findViewById(R.id.questions);
        QuestionAdapter questionAdapter = new QuestionAdapter(this, questions);
        questions_list.setAdapter(questionAdapter);

        setOnClickQuestion(questions_list);
    }

    private void setOnClickQuestion(ListView questions) {
        questions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long l) {
                Question question = (Question) adapter.getItemAtPosition(position);
                if (question.haveAnswer()) {return;}

                Intent intent = new Intent(context, AnswerActivity.class);
                intent.putExtra(ID_QUESTION, question.getId());
                intent.putExtra(ID_PRODUCTO, productId);
                startActivity(intent);
            }
        });
    }

    private void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}
