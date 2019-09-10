package ru.mycompany.NewsApp.ui.activities;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Match;


@EActivity(R.layout.activity_match)
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

    @AfterViews
    void initUI() {
        tv_stadium.setText(match.getStadium());
        tv_date.setText(match.getDate());
        tv_game_type.setText(match.getGameType());
        tv_host_goals.setText(Integer.toString(match.getHostGoals()));
        tv_guest_goals.setText(Integer.toString(match.getGuestGoals()));
        tv_host_team.setText(match.getHost());
        tv_guest_team.setText(match.getGuest());
        tv_review.setText(match.getReview());
        Picasso.with(this)
                .load(match.getHostTeamUrl())
                .into(iv_host_emblem);
        Picasso.with(this)
                .load(match.getGuestTeamUrl())
                .into(iv_guest_emblem);
    }
}
