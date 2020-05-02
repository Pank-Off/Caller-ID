package com.example.Caller_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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
        //Сделать проверку на дубликаты!
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

    public String getSingleUserInfo(String phoneNumber){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 +" = ? ", new String[]{phoneNumber});
        String result = "";

        if (cursor != null && cursor.moveToFirst()) {
            int intIsSpam = cursor.getInt(cursor.getColumnIndex(COL3));
            cursor.close();

            if (intIsSpam == 1) {
                result = "Is spam";
            }

            if (intIsSpam == 0) {
                result = "Not spam";
            }
        }
        else {
            result = "Not found";
        }

        database.close();

        return result;
    }

    public ArrayList<String> getDataFromDB() {
        ArrayList<String> data= new ArrayList<>();
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(COL1);
            int numberColIndex = c.getColumnIndex(COL2);
            int spamColIndex = c.getColumnIndex(COL3);

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                if(!data.contains(c.getString(numberColIndex))) {
                    data.add(c.getString(numberColIndex));
                }
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("DataBase", "0 rows");
        c.close();
        return data;
    }
}
