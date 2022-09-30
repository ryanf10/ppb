package com.example.contact.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;

    private static Database database;
    private static boolean isClose = true;

    private Database(Context context) {
        this.openHelper = new SQLiteOpenHelper(context, "db.sqlite", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };

        this.db = this.openHelper.getWritableDatabase();
        this.db.execSQL("CREATE TABLE IF NOT EXISTS contact(id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, no_hp TEXT)");
    }

    public static Database getInstance(Context context) {
        if (database == null || isClose) {
            database = new Database(context);
            isClose = false;
        }
        return database;
    }

    public void close() {
        if (!isClose && database != null) {
            this.db.close();
            this.openHelper.close();
            isClose = true;
        }
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
