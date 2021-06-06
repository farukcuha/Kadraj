package com.pandorina.kadraj.Models;

public class AuthorsModel {
    private String authorName, authorImage, articleHeader, articlesUrl, status;
    private int newspaperIcon;


    public AuthorsModel() {

    }

    public AuthorsModel(String authorName, String authorImage, String articleHeader, String articlesUrl, int newspaperIcon, String status) {
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.articleHeader = articleHeader;
        this.articlesUrl = articlesUrl;
        this.newspaperIcon = newspaperIcon;
        this.status = status;
    }



    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public String getArticleHeader() {
        return articleHeader;
    }

    public String getArticleUrl() {
        return articlesUrl;
    }

    public int getNewspaperIcon() {
        return newspaperIcon;
    }

    public String getStatus() {
        return status;
    }
}
