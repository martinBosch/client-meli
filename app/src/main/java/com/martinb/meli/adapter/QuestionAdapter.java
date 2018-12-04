package com.martinb.meli.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.martinb.meli.R;
import com.martinb.meli.network.object_request.Question;

import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Question> questions;

    public QuestionAdapter(Activity activity, ArrayList<Question> questions) {
        this.activity = activity;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return this.questions.size();
    }

    @Override
    public Object getItem(int position) {
        return this.questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.question_item, null);
        }
        Question question = this.questions.get(position);

        TextView question_text = (TextView) v.findViewById(R.id.question);
        question_text.setText(question.getQuestion());

        if(question.haveAnswer()) {
            TextView answer_text = (TextView) v.findViewById(R.id.answer);
            answer_text.setText(question.getAnswer());
        }

        return v;
    }
}
