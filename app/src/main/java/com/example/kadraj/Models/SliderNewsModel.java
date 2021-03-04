package com.example.kadraj.Models;

public class SliderNewsModel {
    private String image, url, title;

    public SliderNewsModel() {

    }

    public SliderNewsModel(String image, String url, String title) {
        this.image = image;
        this.url = url;
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
