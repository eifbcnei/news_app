package ru.mycompany.NewsApp.models;

import android.os.Parcel;

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
    @SerializedName("gameType")
    @Expose
    private String gameType;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("guest")
    @Expose
    private String guest;
    @SerializedName("stadium")
    @Expose
    private String stadium;
    @SerializedName("review")
    @Expose
    private String review;

    protected Match(Parcel in) {
        hostTeamUrl = in.readString();
        guestTeamUrl = in.readString();
        hostGoals = in.readInt();
        guestGoals = in.readInt();
        date = in.readString();
        gameType = in.readString();
        host = in.readString();
        guest = in.readString();
        stadium = in.readString();
        review = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hostTeamUrl);
        dest.writeString(guestTeamUrl);
        dest.writeInt(hostGoals);
        dest.writeInt(guestGoals);
        dest.writeString(date);
        dest.writeString(gameType);
        dest.writeString(host);
        dest.writeString(guest);
        dest.writeString(stadium);
        dest.writeString(review);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public String getReview() {
        return review;
    }


    public String getGameType() {
        return gameType;
    }

    public String getHost() {
        return host;
    }

    public String getGuest() {
        return guest;
    }

    public String getStadium() {
        return stadium;
    }

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
