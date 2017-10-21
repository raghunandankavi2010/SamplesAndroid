package com.peakaeriest.ladyspyder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LSFAQModel {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("FAQ")
    @Expose
    private List<FAQ> fAQ = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<FAQ> getFAQ() {
        return fAQ;
    }

    public void setFAQ(List<FAQ> fAQ) {
        this.fAQ = fAQ;
    }

    public class FAQ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("answer")
        @Expose
        private String answer;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

    }
}