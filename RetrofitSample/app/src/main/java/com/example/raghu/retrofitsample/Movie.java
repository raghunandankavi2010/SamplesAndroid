package com.example.raghu.retrofitsample;

/**
 * Created by raghu on 23/4/17.
 */
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class Movie
{
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("realease_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds=new ArrayList<Integer>();
    @SerializedName("id")
    private int id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("vote_count")
    private int vote_count;
    @SerializedName("vedeo")
    private boolean vedeo;
    @SerializedName("vote_average")
    private Double vote_average;


    public Movie(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, Integer id,
                 String originalTitle, String original_language, String title, String backdrop_path, Double popularity,
                 Integer vote_count, Boolean vedeo, Double vote_average) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.vedeo = vedeo;
        this.vote_average = vote_average;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public void setOriginalLanguage(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdrop_path = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getvote_count() {
        return vote_count;
    }

    public void setVoteCount(Integer voteCount) {
        this.vote_count = voteCount;
    }

    public Boolean getVedeo() {
        return vedeo;
    }

    public void setVedeo(Boolean vedeo) {
        this.vedeo = vedeo;
    }

    public Double getvote_average() {
        return vote_average;
    }

    public void setVoteAverage(Double vote_average) {
        this.vote_average = vote_average;
    }
}
