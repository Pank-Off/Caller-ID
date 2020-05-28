package com.example.Caller_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "newPhoneTable";
    private static final String COL1 = "ID";
    private static final String COL2 = "phoneNumber";
    private static final String COL3 = "isSpam";
    private static final String COL4 = "comment";

    DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " NUMERIC, " +
                COL4 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_NAME);
        onCreate(db);
    }

    public boolean addRecord(String phoneNumber, Boolean isSpam, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean duplicateIsFound = checkDuplicate(db, phoneNumber);
        if (duplicateIsFound) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, phoneNumber);
        contentValues.put(COL4, comment);
        if (isSpam) {
            contentValues.put(COL3, 1);
        } else {
            contentValues.put(COL3, 0);
        }

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    private boolean checkDuplicate(SQLiteDatabase db, String phoneNumber) {
        Cursor cursor = db.query(TABLE_NAME, null, COL2 + "=?", new String[]{phoneNumber}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }

    public void replaceRecord(String oldNumber, String newNumber, String newComment) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = ? ", new String[]{oldNumber});
        String oldComment = "";
        if (cursor != null && cursor.moveToFirst()) {
            oldComment = cursor.getString(cursor.getColumnIndex(COL4));
            cursor.close();
        }
        String updateNumber = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = replace(" + COL2 + ", '" + oldNumber + "', '" + newNumber + "') WHERE " + COL2 + " LIKE '%" + oldNumber + "%'";
        db.execSQL(updateNumber);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, newComment);
        int result = db.update(TABLE_NAME, contentValues, COL4 + " = ? ", new String[]{oldComment});
        Log.d("result", result + "");
        db.close();
    }

    public boolean removeRecord(String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        int success = db.delete(TABLE_NAME, COL2 + "=?", new String[]{phoneNumber});
        return success == 1;
    }

    public String getSingleUserInfo(String phoneNumber) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL2 + " = ? ", new String[]{phoneNumber});
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
        } else {
            result = "Not found";
        }

        // database.close();

        return result;
    }

    public HashMap<String, String> getDataFromDB() {
        HashMap<String, String> data = new HashMap<>();
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
            int commentColIndex = c.getColumnIndex(COL4);

            do {
                // получаем значения по номерам столбцов и пишем все в лог

                if (!data.containsKey(c.getString(numberColIndex))) {
                    data.put(c.getString(numberColIndex), c.getString(commentColIndex));
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
