package ru.mycompany.NewsApp.ui.activities;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Article;
import ru.mycompany.NewsApp.ui.adapters.BonusPhotoAdapter;

@EActivity(R.layout.activity_article)
public class ArticleActivity extends AppCompatActivity {
    @ViewById
    RecyclerView rv_bonus_photos;
    @ViewById
    Toolbar toolbar;
    @ViewById
    ImageView iv_main_photo;
    @ViewById
    TextView tv_title;
    @ViewById
    TextView tv_content;
    @Extra("Article")
    Article article;

    @Click(R.id.ib_share)
    void onShare() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "thisAppSite.com/thisArticle");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @AfterViews
    void initUI() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(this)
                .load(article.getMainPhotoUrl())
                .into(iv_main_photo);
        tv_title.setText(article.getTitle());
        tv_content.setText(article.getContent());
        BonusPhotoAdapter adapter = new BonusPhotoAdapter(this, article.getBonusPhotosUrls());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rv_bonus_photos.setLayoutManager(manager);
        rv_bonus_photos.setAdapter(adapter);
    }
}
