package com.example.raghu.retrofitsample;


import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("product_name_en")
    private String name;

    @SerializedName("brands")
    private String company;

    @SerializedName("update_key")
    private int key;

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public int getKey() {
        return key;
    }
}