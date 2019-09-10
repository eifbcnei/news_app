package ru.mycompany.NewsApp.models;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article implements NewsItemModel {
    public final static int TYPE = 1;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("mainPhotoUrl")
    @Expose
    private String mainPhotoUrl;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("bonusPhotosUrls")
    @Expose
    private List<String> bonusPhotosUrls;

    protected Article(Parcel in) {
        title = in.readString();
        content = in.readString();
        mainPhotoUrl = in.readString();
        source = in.readString();
        description = in.readString();
        bonusPhotosUrls = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(mainPhotoUrl);
        dest.writeString(source);
        dest.writeString(description);
        dest.writeStringList(bonusPhotosUrls);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public List<String> getBonusPhotosUrls() {
        return bonusPhotosUrls;
    }


    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public String getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public String getSource() {
        return source;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
