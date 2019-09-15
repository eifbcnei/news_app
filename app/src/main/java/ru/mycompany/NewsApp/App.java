package ru.mycompany.NewsApp;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context appContext() {
        return instance.getApplicationContext();
    }
}
