package com.example.Caller_ID;

import android.app.Application;

public class App extends Application {

    private static App instance;

    // база данных
    private DatabaseHelper db;

    // Так получаем объект приложения
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Это для синглтона, сохраняем объект приложения
        instance = this;
        db = new DatabaseHelper(this);
    }

    public DatabaseHelper getDataBase() {
        return db;
    }
}
