package ru.mycompany.NewsApp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match implements NewsItemModel, Parcelable {
    public final static int TYPE = 0;
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
    @SerializedName("stadium")
    @Expose
    private String stadium;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("host")
    @Expose
    private String host_key;
    private transient Team host;
    @SerializedName("guest")
    @Expose
    private String guest_key;
    private transient Team guest;


    public void setHost(Team host) {
        this.host = host;
    }

    public void setGuest(Team guest) {
        this.guest = guest;
    }

    public String getHost_key() {
        return host_key;
    }

    public String getGuest_key() {
        return guest_key;
    }

    protected Match(Parcel in) {
        hostGoals = in.readInt();
        guestGoals = in.readInt();
        date = in.readString();
        gameType = in.readString();
        stadium = in.readString();
        review = in.readString();
        host = in.readParcelable(Team.class.getClassLoader());
        guest = in.readParcelable(Team.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hostGoals);
        dest.writeInt(guestGoals);
        dest.writeString(date);
        dest.writeString(gameType);
        dest.writeString(stadium);
        dest.writeString(review);
        dest.writeParcelable(host, flags);
        dest.writeParcelable(guest, flags);
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

    @Override
    public int getType() {
        return TYPE;
    }

    public Team getHost() {
        return host;
    }

    public Team getGuest() {
        return guest;
    }
}
