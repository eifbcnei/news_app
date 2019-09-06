package ru.mycompany.NewsApp.models;

import android.graphics.Bitmap;

public class Article implements NewsItemModel {
    public final static int TYPE = 1;
    private String title;
    private String content;
    private Bitmap photo;
    private String source;

    public Article(String title, String content, String source, Bitmap photo) {
        this.title = title;
        this.content = content;
        this.source = source;
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getSource() {
        return source;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
