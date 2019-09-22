package ru.mycompany.NewsApp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    private static SharedPreferences instance = PreferenceManager.getDefaultSharedPreferences(App.appContext());
    private static final String THEME_PREFERENCE = "pref_night_theme";
    private static final String TAGS_PREFERENCE = "pref_tags_visibility";
    private static final int DEFAULT_THEME_ID = R.style.AppTheme_Day;
    private static final int NIGHT_THEME_ID = R.style.AppTheme_Night;

    public static boolean isNightTheme() {
        return getCurrentTheme() == NIGHT_THEME_ID;
    }

    public static int getCurrentTheme() {
        return instance.getBoolean(THEME_PREFERENCE, false) ? NIGHT_THEME_ID : DEFAULT_THEME_ID;
    }

    public static boolean areTagsVisible() {
        return instance.getBoolean(TAGS_PREFERENCE, true);
    }
}
