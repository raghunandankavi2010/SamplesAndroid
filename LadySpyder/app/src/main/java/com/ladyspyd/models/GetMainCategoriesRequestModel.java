package com.ladyspyd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMainCategoriesRequestModel {

    @SerializedName("language_id")
    @Expose
    public String languageId;

    /**
     * No args constructor for use in serialization
     */
    public GetMainCategoriesRequestModel() {
    }

    /**
     * @param languageId
     */
    public GetMainCategoriesRequestModel(String languageId) {
        super();
        this.languageId = languageId;
    }

}