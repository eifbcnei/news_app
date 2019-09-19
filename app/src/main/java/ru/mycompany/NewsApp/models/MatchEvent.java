package ru.mycompany.NewsApp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchEvent implements Parcelable {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("minute")
    @Expose
    private int minute;
    @SerializedName("player")
    @Expose
    private String player;
    @SerializedName("isHostTeamPlayer")
    @Expose
    private boolean isHostTeamPlayer;

    protected MatchEvent(Parcel in) {
        type = in.readString();
        minute = in.readInt();
        player = in.readString();
        isHostTeamPlayer = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeInt(minute);
        dest.writeString(player);
        dest.writeByte((byte) (isHostTeamPlayer ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MatchEvent> CREATOR = new Creator<MatchEvent>() {
        @Override
        public MatchEvent createFromParcel(Parcel in) {
            return new MatchEvent(in);
        }

        @Override
        public MatchEvent[] newArray(int size) {
            return new MatchEvent[size];
        }
    };

    public String getType() {
        return type;
    }

    public int getMinute() {
        return minute;
    }

    public String getPlayer() {
        return player;
    }

    public boolean isHostTeamPlayer() {
        return isHostTeamPlayer;
    }

    @Override
    public String toString() {
        return "MatchEvent{" +
                "type='" + type + '\'' +
                ", minute=" + minute +
                ", player='" + player + '\'' +
                ", isHostTeamPlayer=" + isHostTeamPlayer +
                '}';
    }
}