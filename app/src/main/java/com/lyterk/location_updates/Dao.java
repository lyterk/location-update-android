/*
package com.lyterk.location_updates;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dao extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_NAME = "location_storage";
    private static final String DICTIONARY_TABLE_CREATE = "CREATE TABLE" + DICTIONARY_TABLE_NAME +
            " (Id INTEGER PRIMARY KEY, Latitude TEXT, Longitude TEXT, Time TIMESTAMP);";

    Dao(Context context) {
        super(context, DICTIONARY_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, holder int, holder2 int) {}

    // TODO Store location information in SQLite database
}
*/