package com.peakaeriest.ladyspyder.models;

/**
 * Created by raghu on 14/10/17.
 */

public class Product {

    private String image_url,content,price,title_url,discount;
    private float stars;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTitle_url(String title_url) {
        this.title_url = title_url;
    }

    public String getTitle_url() {
        return title_url;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount() {
        return discount;
    }
}
