package com.example.juhi.newsapp.model;

/**
 * Created by juhi on 6/29/17.
 */

public class NewsItem {

    private String title;
    private String author;
    private String description;
    private String time;
    private String url;
    private String urlToImage;


//title,author,description,url,time,urlToImage
    public NewsItem(String title, String author, String description, String url, String time, String urlToImage) {
        this.time = time;
        this.title = title;
        this.description = description;
        this.url = url;
        this.author = author;
        this.urlToImage = urlToImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
