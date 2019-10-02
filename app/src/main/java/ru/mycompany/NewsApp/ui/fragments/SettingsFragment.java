package ru.mycompany.NewsApp.ui.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import ru.mycompany.NewsApp.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String THEME_PREFERENCE = "pref_night_theme";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //when theme changes show instantly
        if (key.equals(THEME_PREFERENCE)) {
            Activity mCurrentActivity = getActivity();
            mCurrentActivity.finish();
            //show animation instead of black screen during 'recreate'
            mCurrentActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            mCurrentActivity.startActivity(mCurrentActivity.getIntent());
        }
    }
}