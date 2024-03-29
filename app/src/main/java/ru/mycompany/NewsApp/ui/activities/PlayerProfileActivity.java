package ru.mycompany.NewsApp.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import ru.mycompany.NewsApp.AppPreferences;
import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.models.Player;
import ru.mycompany.NewsApp.ui.adapters.playerattributes.PlayerAttributesAdapter;
import ru.mycompany.NewsApp.ui.customviews.CircleImageView;

@EActivity
public class PlayerProfileActivity extends AppCompatActivity {
    @ViewById
    CircleImageView iv_avatar;
    @Extra("Player")
    Player player;
    @ViewById
    RecyclerView rv_player_attrs;
    //if image is downloaded for the first time
    //picasso might not set it into target
    //if using .into(iv_avatar)
    private Target target = new Target() {
        @Override
        public void onSuccess(Bitmap bitmap) {
            iv_avatar.setImageBitmap(bitmap);
        }

        @Override
        public void onError() {
            Toast.makeText(PlayerProfileActivity.this, R.string.bad_internet_warning, Toast.LENGTH_LONG).show();
        }
    };

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

        Picasso.with(this)
                .load(player.getAvatar())
                .into(target);

        PlayerAttributesAdapter adapter = new PlayerAttributesAdapter(this, player);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_player_attrs.setLayoutManager(manager);
        rv_player_attrs.setAdapter(adapter);
    }
}
