package com.peakaeriest.ladyspyder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetMainCategoryResponse implements Serializable {
    public class SubCategory implements Serializable {

        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("sub_category_id")
        @Expose
        private String subCategoryId;
        @SerializedName("sub_category_name")
        @Expose
        private String subCategoryName;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

    }

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("getcategory")
    @Expose
    private List<List<Getcategory>> getcategory = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<List<Getcategory>> getGetcategory() {
        return getcategory;
    }

    public void setGetcategory(List<List<Getcategory>> getcategory) {
        this.getcategory = getcategory;
    }

    public class Getcategory  implements Serializable{

        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("sub_category")
        @Expose
        private List<SubCategory> subCategory = null;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<SubCategory> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(List<SubCategory> subCategory) {
            this.subCategory = subCategory;
        }

    }

}