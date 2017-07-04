package com.example.juhi.newsapp.model;

/**
 * Created by juhi on 6/29/17.
 */

public class NewsItem {

    private String title;
    private String description;
    private String time;
    private String url;


    public NewsItem(String title, String description, String url, String time) {
        this.time = time;
        this.title = title;
        this.description = description;
        this.url = url;
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
