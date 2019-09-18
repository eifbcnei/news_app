package ru.mycompany.NewsApp.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.mycompany.NewsApp.AppPreferences;
import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Match;
import ru.mycompany.NewsApp.models.Team;


@EActivity
public class MatchActivity extends AppCompatActivity {
    @Extra("Match")
    Match match;
    @ViewById
    TextView tv_stadium;
    @ViewById
    TextView tv_date;
    @ViewById
    TextView tv_game_type;
    @ViewById
    TextView tv_host_goals;
    @ViewById
    TextView tv_guest_goals;
    @ViewById
    ImageView iv_host_emblem;
    @ViewById
    ImageView iv_guest_emblem;
    @ViewById
    TextView tv_host_team;
    @ViewById
    TextView tv_guest_team;
    @ViewById
    TextView tv_review;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(AppPreferences.getCurrentTheme());
        setContentView(R.layout.activity_match);
    }

    @AfterViews
    void initUI() {
        tv_stadium.setText(match.getStadium());
        tv_date.setText(match.getDate());
        tv_game_type.setText(match.getGameType());
        tv_host_goals.setText(Integer.toString(match.getHostGoals()));
        tv_guest_goals.setText(Integer.toString(match.getGuestGoals()));
        Team host = match.getHost();
        tv_host_team.setText(host.getName());
        Team guest = match.getGuest();
        tv_guest_team.setText(guest.getName());
        tv_review.setText(match.getReview());
        Picasso.with(this)
                .load(host.getEmblem())
                .into(iv_host_emblem);
        Picasso.with(this)
                .load(guest.getEmblem())
                .into(iv_guest_emblem);
    }

    @Click(R.id.iv_host_emblem)
    void openHostSite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(match.getHost().getSite()));
        startActivity(browserIntent);
    }

    @Click(R.id.iv_guest_emblem)
    void openGuestSite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(match.getGuest().getSite()));
        startActivity(browserIntent);
    }
}
