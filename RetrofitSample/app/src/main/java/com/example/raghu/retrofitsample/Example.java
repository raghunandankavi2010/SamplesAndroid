package com.example.raghu.retrofitsample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghu on 03-02-2017.
 */

public class Example {

    @SerializedName("last_question")
    @Expose
    private String lastQuestion;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("error")
    @Expose
    private String error;

    public String getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(String lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}