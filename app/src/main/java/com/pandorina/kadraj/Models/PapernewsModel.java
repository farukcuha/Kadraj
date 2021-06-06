package com.pandorina.kadraj.Models;

public class PapernewsModel {
    private int image;
    private String url, newspaperName;


    public PapernewsModel() {

    }

    public PapernewsModel(int image, String url, String newspaperName) {
        this.image = image;
        this.url = url;
        this.newspaperName = newspaperName;
    }

    public int getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getNewspaperName() {
        return newspaperName;
    }
}
