package ru.mycompany.NewsApp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute implements Parcelable {
    @SerializedName("attribute")
    @Expose
    private String attribute;
    @SerializedName("value")
    @Expose
    private String value;

    protected Attribute(Parcel in) {
        attribute = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attribute);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Attribute> CREATOR = new Creator<Attribute>() {
        @Override
        public Attribute createFromParcel(Parcel in) {
            return new Attribute(in);
        }

        @Override
        public Attribute[] newArray(int size) {
            return new Attribute[size];
        }
    };

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }
}