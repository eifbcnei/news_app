package ru.mycompany.NewsApp.ui.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
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
    //@Bean
    RecyclerView.Adapter mAdapter;
    @Extra("Article")
    Article article;

    @AfterViews
    void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView main_photo = findViewById(R.id.iv_main_photo);
        Picasso.with(this)
                .load(article.getMainPhotoUrl())
                .into(main_photo);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(article.getTitle());
        TextView tv_content = findViewById(R.id.tv_content);
        tv_content.setText(article.getContent());

        BonusPhotoAdapter adapter = new BonusPhotoAdapter(this, article.getBonusPhotosUrls());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rv_bonus_photos.setLayoutManager(manager);
        rv_bonus_photos.setAdapter(adapter);
        ImageButton share_btn = findViewById(R.id.ib_share);
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "thisAppSite.com/thisArticle");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }
}
