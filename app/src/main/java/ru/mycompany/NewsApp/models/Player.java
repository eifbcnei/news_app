package ru.mycompany.NewsApp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Player implements Parcelable {
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes;

    protected Player(Parcel in) {
        avatar = in.readString();
        attributes = in.createTypedArrayList(Attribute.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar);
        dest.writeTypedList(attributes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }
}
