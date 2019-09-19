package ru.mycompany.NewsApp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("host")
    @Expose
    private String host_key;
    private Team host;
    @SerializedName("guest")
    @Expose
    private String guest_key;
    @SerializedName("tags")
    @Expose
    List<String> tags;
    private Team guest;
    @SerializedName("matchEvents")
    @Expose
    private List<MatchEvent> matchEvents;


    protected Match(Parcel in) {
        hostGoals = in.readInt();
        guestGoals = in.readInt();
        date = in.readString();
        gameType = in.readString();
        stadium = in.readString();
        host_key = in.readString();
        host = in.readParcelable(Team.class.getClassLoader());
        guest_key = in.readString();
        tags = in.createStringArrayList();
        guest = in.readParcelable(Team.class.getClassLoader());
        matchEvents = in.createTypedArrayList(MatchEvent.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hostGoals);
        dest.writeInt(guestGoals);
        dest.writeString(date);
        dest.writeString(gameType);
        dest.writeString(stadium);
        dest.writeString(host_key);
        dest.writeParcelable(host, flags);
        dest.writeString(guest_key);
        dest.writeStringList(tags);
        dest.writeParcelable(guest, flags);
        dest.writeTypedList(matchEvents);
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

    public List<MatchEvent> getMatchEvents() {
        return matchEvents;
    }

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

    @Override
    public List<String> getTags() {
        return tags;
    }

    public Team getGuest() {
        return guest;
    }

    @Override
    public String toString() {
        return "Match{" +
                "hostGoals=" + hostGoals +
                ", guestGoals=" + guestGoals +
                ", date='" + date + '\'' +
                ", gameType='" + gameType + '\'' +
                ", stadium='" + stadium + '\'' +
                ", host_key='" + host_key + '\'' +
                ", host=" + host +
                ", guest_key='" + guest_key + '\'' +
                ", tags=" + tags +
                ", guest=" + guest +
                ", matchEvents=" + matchEvents +
                '}';
    }
}
