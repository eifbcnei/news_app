package ru.mycompany.NewsApp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreferences {

    private static SharedPreferences instance = PreferenceManager.getDefaultSharedPreferences(App.appContext());
    private static final String THEME_ID = "THEME_ID";
    private static final int DEFAULT_THEME_ID = R.style.AppTheme_Day;
    private static final int NIGHT_THEME_ID = R.style.AppTheme_Night;

    public static void switchTheme() {
        if (isSetNightTheme()) {
            instance.edit().putInt(THEME_ID, DEFAULT_THEME_ID).apply();
        } else {
            instance.edit().putInt(THEME_ID, NIGHT_THEME_ID).apply();
        }
    }

    public static boolean isSetNightTheme() {
        return getCurrentTheme() == NIGHT_THEME_ID;
    }

    public static int getCurrentTheme() {
        return instance.getInt(THEME_ID, DEFAULT_THEME_ID);
    }
}
