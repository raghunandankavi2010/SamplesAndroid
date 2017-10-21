package com.peakaeriest.ladyspyder.models;

/**
 * Created by Lincoln on 18/05/16.
 */
public class LSPopularProductPojo {
    private String name;
    private int numOfSongs;
    private int thumbnail;

    public LSPopularProductPojo() {
    }

    public LSPopularProductPojo(String name, int numOfSongs, int thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
