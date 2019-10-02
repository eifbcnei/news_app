package ru.mycompany.NewsApp.ui.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.mycompany.NewsApp.AppPreferences;
import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Player;
import ru.mycompany.NewsApp.ui.adapters.playerattributes.PlayerAttributesAdapter;

@EActivity
public class PlayerProfileActivity extends AppCompatActivity {
    @ViewById
    ImageView iv_avatar;
    @Extra("Player")
    Player player;
    @ViewById
    RecyclerView rv_player_attrs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(AppPreferences.getCurrentTheme());
        setContentView(R.layout.activity_player_profile);
    }

    @AfterViews
    void initUI() {
        if (player == null) {
            Toast.makeText(this, R.string.bad_internet_warning, Toast.LENGTH_LONG).show();
            return;
        }

        PlayerAttributesAdapter adapter = new PlayerAttributesAdapter(this, player);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_player_attrs.setLayoutManager(manager);
        rv_player_attrs.setAdapter(adapter);
        Picasso.with(this)
                .load(player.getAvatar())
                .into(iv_avatar);
    }

}
