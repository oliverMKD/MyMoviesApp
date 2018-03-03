package com.oliver.mymovies.klasi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Oliver on 2/8/2018.
 */

public class Film implements Serializable {


    public int id;
    public boolean favorite;
    public boolean watchlist;
    public transient boolean rated;

    public int budget;
    public int revenue;

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private double averageVote;

    @SerializedName("vote_count")
    private long voteCount;

    @SerializedName("backdrop_path")
    private String backdropPath;

   public String original_language;

    public Credits credits;

    String baseImageUrl = "http://image.tmdb.org/t/p/w500";

    public String getPosterPath(){
        return "http://image.tmdb.org/t/p/w500" + posterPath;
    }

    public Film() {
    }

    public Film(int budget, int revenue,  int id, String originalTitle, String overview, String releaseDate, double popularity, String title, double averageVote, long voteCount, String backdropPath, String baseImageUrl) {
        this.budget = budget;
        this.revenue = revenue;
        this.id = id;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.title = title;
        this.averageVote = averageVote;
        this.voteCount = voteCount;
        this.backdropPath = backdropPath;
        this.baseImageUrl = baseImageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(double averageVote) {
        this.averageVote = averageVote;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }
}
