package com.example.gaper.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "activity_database";
    private static int DATABASE_VERSION = 1;
    private static String TABLE_NAME = "words";
    private static String C_ID = "id";
    private static String C_WORD = "word";

    private SQLiteDatabase database;


    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + C_ID + " INTEGER PRIMARY KEY," + C_WORD + " TEXT);";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close();
    }

    public void clear() {
        open();
        database.execSQL("delete from " + TABLE_NAME);
        close();
    }

    public void insertScore() {
        ContentValues content;
        open();
        for (String myWord : Words.getWords()) {
            if (myWord.length() > 0) {
                content = new ContentValues();
                content.put(C_WORD, myWord);
                database.insert(TABLE_NAME, null, content);
            }
        }
        close();
    }

    public Cursor getWordFromTable(int wordIndex) {
        open();
        String index = wordIndex + "";
        String query = "SELECT "  + C_ID + ", " + C_WORD + " from " + TABLE_NAME + " WHERE " + C_ID + " = ?  ORDER BY " + C_ID;
        return database.rawQuery(query, new String[] {index});
    }




}
