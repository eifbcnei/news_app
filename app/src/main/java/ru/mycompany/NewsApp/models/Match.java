package ru.mycompany.NewsApp.models;

import android.graphics.Bitmap;

public class Match implements NewsItemModel {
    public final static int TYPE = 0;
    private Bitmap hostTeam;
    private Bitmap guestTeam;
    private int hostGoals, guestGoals;

    public Match(Bitmap hostTeam, Bitmap guestTeam, int hostGoals, int guestGoals) {
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.hostGoals = hostGoals;
        this.guestGoals = guestGoals;
    }

    public Bitmap getHostTeam() {
        return hostTeam;
    }

    public Bitmap getGuestTeam() {
        return guestTeam;
    }

    public int getHostGoals() {
        return hostGoals;
    }

    public int getGuestGoals() {
        return guestGoals;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
