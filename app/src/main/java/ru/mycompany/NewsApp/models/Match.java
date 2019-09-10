package ru.mycompany.NewsApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match implements NewsItemModel {
    public final static int TYPE = 0;
    @SerializedName("hostTeamUrl")
    @Expose
    private String hostTeamUrl;
    @SerializedName("guestTeamUrl")
    @Expose
    private String guestTeamUrl;
    @SerializedName("hostGoals")
    @Expose
    private int hostGoals;
    @SerializedName("guestGoals")
    @Expose
    private int guestGoals;
    @SerializedName("date")
    @Expose
    private String date;


    public String getDate() {
        return date;
    }

    public int getHostGoals() {
        return hostGoals;
    }

    public int getGuestGoals() {
        return guestGoals;
    }

    public String getHostTeamUrl() {
        return hostTeamUrl;
    }

    public String getGuestTeamUrl() {
        return guestTeamUrl;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public String toString() {
        return "Match{" +
                "hostTeamUrl='" + hostTeamUrl + '\'' +
                ", guestTeamUrl='" + guestTeamUrl + '\'' +
                ", hostGoals=" + hostGoals +
                ", guestGoals=" + guestGoals +
                '}';
    }
}
