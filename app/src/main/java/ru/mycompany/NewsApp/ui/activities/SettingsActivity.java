package ru.mycompany.NewsApp.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.mycompany.NewsApp.AppPreferences;
import ru.mycompany.NewsApp.R;
import ru.mycompany.NewsApp.ui.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(AppPreferences.getCurrentTheme());
        setContentView(R.layout.activity_settings);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.m_container, new SettingsFragment())
                    .commit();
        }
    }
}
