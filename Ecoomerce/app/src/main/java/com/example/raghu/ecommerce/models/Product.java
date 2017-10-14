package com.example.raghu.ecommerce.models;

import org.jsoup.nodes.Element;

/**
 * Created by raghu on 14/10/17.
 */

public class Product {

    private String image_url,content,price;
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

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }
}
