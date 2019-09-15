package ru.mycompany.NewsApp.models;

import android.os.Parcelable;

import java.util.List;

public interface NewsItemModel extends Parcelable {
    List<String> getTags();

    int getType();
}
