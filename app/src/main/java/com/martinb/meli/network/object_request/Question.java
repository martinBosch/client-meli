package com.martinb.meli.network.object_request;

public class Question {

    private String _id;
    private String question;
    private String answer;

    public Question(String question) {
        this.question = question;
        this.answer = null;
    }

    public String getId() {
        return this._id;
    }

    public String getQuestion() {
        return this.question;
    }

    public boolean haveAnswer() {
        return this.answer != null;
    }

    public String getAnswer() {
        return this.answer;
    }
}
