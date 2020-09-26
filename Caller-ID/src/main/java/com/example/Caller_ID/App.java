package com.example.Caller_ID;

import android.app.Application;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class
App extends Application {

    private static App instance;
    FireBaseWorker fireBaseWorker;
    // база данных
    private DatabaseHelper db;

    // Так получаем объект приложения
    public static App getInstance() {
        return instance;
    }

    public static final String APP_PREFERENCES = "callerIDPreferences";

    @Override
    public void onCreate() {
        super.onCreate();

        // Это для синглтона, сохраняем объект приложения
        instance = this;
        db = new DatabaseHelper(this);

        DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.US);
        String currentDate = df.format(new Date());
        String dateTimeKey = "lastUpdateDate";

        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, 0);

        if (sharedPreferences.getBoolean("firstRun", true)) {
            sharedPreferences.edit().putBoolean("firstRun", false).apply();
            fireBaseWorker = new FireBaseWorker(getApplicationContext());
            fireBaseWorker.download();

            sharedPreferences.edit().putString(dateTimeKey, currentDate).apply();
            sharedPreferences.edit().apply();
        } else {
            String lastUpdateDate = sharedPreferences.getString(dateTimeKey, currentDate);
            if (!currentDate.equals(lastUpdateDate)) {
                fireBaseWorker = new FireBaseWorker(getApplicationContext());
                fireBaseWorker.download();

                sharedPreferences.edit().putString(dateTimeKey, currentDate).apply();
                sharedPreferences.edit().apply();
            }
        }
    }

    public DatabaseHelper getDataBase() {
        return db;
    }
}
