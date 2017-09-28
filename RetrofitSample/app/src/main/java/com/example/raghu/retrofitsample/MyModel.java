
package com.example.raghu.retrofitsample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyModel {

    @SerializedName("r030")
    @Expose
    private long r030;
    @SerializedName("txt")
    @Expose
    private String txt;
    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("cc")
    @Expose
    private String cc;
    @SerializedName("exchangedate")
    @Expose
    private String exchangedate;

    public long getR030() {
        return r030;
    }

    public void setR030(long r030) {
        this.r030 = r030;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExchangedate() {
        return exchangedate;
    }

    public void setExchangedate(String exchangedate) {
        this.exchangedate = exchangedate;
    }




}

