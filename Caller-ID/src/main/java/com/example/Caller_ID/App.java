package com.example.Caller_ID;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class App extends Application {

    private static App instance;
    FireBaseWorker fireBaseWorker;

    // база данных
    private DatabaseHelper db;

    SharedPreferences sPref;

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

        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String currentDate = df.format(new Date());
        String dateTimeKey = "lastUpdateDate";

        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, 0);

        if (sharedPreferences.getBoolean("firstRun", true)) {
            sharedPreferences.edit().putBoolean("firstRun", false).commit();
            fireBaseWorker = new FireBaseWorker(getApplicationContext());
            fireBaseWorker.download();

            sharedPreferences.edit().putString(dateTimeKey, currentDate);
            sharedPreferences.edit().apply();
        }
        else {
            String lastUpdateDate = sharedPreferences.getString(dateTimeKey, currentDate);
            Date lastDate;
            try {
                lastDate = df.parse(lastUpdateDate);
                if (!currentDate.equals(lastUpdateDate)) {
                    fireBaseWorker = new FireBaseWorker(getApplicationContext());
                    fireBaseWorker.download();

                    sharedPreferences.edit().putString(dateTimeKey, currentDate);
                    sharedPreferences.edit().apply();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public DatabaseHelper getDataBase() {
        return db;
    }
}
