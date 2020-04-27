package com.example.Caller_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "phoneTable";
    private static final String COL1 = "ID";
    private static final String COL2 = "phoneNumber";
    private static final String COL3 = "isSpam";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " NUMERIC)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(TABLE_NAME);
        onCreate(db);
    }

    public boolean addRecord(String phoneNumber, Boolean isSpam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, phoneNumber);

        if (isSpam) {
            contentValues.put(COL3, 1);
        } else {
            contentValues.put(COL3, 0);
        }

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }
}
