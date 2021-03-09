package com.example.kadraj.Models;

public class NewsCategoryModel{
    private String newsUrl, newsImage, newsName;

    public NewsCategoryModel() {

    }
    public NewsCategoryModel(String newsName, String newsImage, String newsUrl) {
        this.newsUrl = newsUrl;
        this.newsImage = newsImage;
        this.newsName = newsName;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public String getNewsName() {
        return newsName;
    }
}
