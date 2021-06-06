package com.pandorina.kadraj.Models;

public class NewsCategoryModel{
    private String newsUrl, newsName;
    private int newsImage;

    public NewsCategoryModel() {

    }
    public NewsCategoryModel(String newsName, int newsImage, String newsUrl) {
        this.newsUrl = newsUrl;
        this.newsImage = newsImage;
        this.newsName = newsName;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public String getNewsName() {
        return newsName;
    }
}
