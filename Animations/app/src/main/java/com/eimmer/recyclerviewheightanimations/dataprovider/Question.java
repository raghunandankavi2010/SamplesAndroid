package com.eimmer.recyclerviewheightanimations.dataprovider;

public class Question {
    private String question;
    private String answer;

    public Question(final String question, final String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
