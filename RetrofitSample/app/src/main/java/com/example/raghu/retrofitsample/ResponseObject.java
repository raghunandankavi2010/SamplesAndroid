package com.example.raghu.retrofitsample;

import com.example.raghu.retrofitsample.Product;
import com.google.gson.annotations.SerializedName;

public class ResponseObject{
    @SerializedName("status")
    private int status;

    @SerializedName("status_verbose")
    private String status_verbose;

    @SerializedName("product")
    private Product product;

    public Product getProduct() {
        return product;
    }

    //getters and setters goes here
}