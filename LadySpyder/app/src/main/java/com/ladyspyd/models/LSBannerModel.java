package com.ladyspyd.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LSBannerModel {

    @SerializedName("Error")
    @Expose
    private Boolean error;
    @SerializedName("Banners")
    @Expose
    private List<Banner> banners = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public class Banner {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }

    }
}