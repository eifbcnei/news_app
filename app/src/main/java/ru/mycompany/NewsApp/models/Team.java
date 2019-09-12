package ru.mycompany.NewsApp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("emblem")
    @Expose
    private String emblem;
    @SerializedName("site")
    @Expose
    private String site;

    protected Team(Parcel in) {
        name = in.readString();
        emblem = in.readString();
        site = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(emblem);
        dest.writeString(site);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getEmblem() {
        return emblem;
    }

    public String getSite() {
        return site;
    }
}
