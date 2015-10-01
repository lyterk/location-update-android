package com.lyterk.location_updates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Dao extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final int DATABASE_VERSION = 2;
    private static final String DICTIONARY_NAME = "location_storage";
    private static final String DICTIONARY_TABLE_CREATE = "CREATE TABLE" + DICTIONARY_NAME +
            " (Id INTEGER PRIMARY KEY AUTOINCREMENT, Latitude TEXT NOT NULL, Longitude " +
            "TEXT NOT NULL, Time TIMESTAMP NOT NULL);";
    private static final String DROP_IF_EXISTS = "DROP TABLE IF EXISTS " + DICTIONARY_NAME + ";";

    Dao(Context context) {
        super(context, DICTIONARY_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(DROP_IF_EXISTS);
        onCreate(db);
    }

    public void close() {
        db.close();
    }

    public void createLocation(LocationData ld) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Latitude", ld.mLatitude);
        contentValues.put("Longitude", ld.mLongitude);
        contentValues.put("Time", ld.mSqlDate.toString());

        db.insert(DICTIONARY_NAME, null, contentValues);
    }

    public void deleteLocation(int locationId) {
        db.delete(DICTIONARY_NAME, "Id = " + locationId, null);
    }

    public List<List<String>> fetchLocationData(Cursor cursor) {
        // TODO Figure out how to make fetchLocationData() accept different query params
        Cursor mCursorSelectAll = db.query(DICTIONARY_NAME, tableColumns, null, null, null, null, null);
        String[] tableColumns = {"id", "Latitude", "Longitude", "Time"};
        List queryList = new ArrayList();

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            List<String> rowList = new ArrayList();
            int index = cursor.getInt(0);
            rowList.add(cursor.getString(1));
            rowList.add(cursor.getString(2));
            rowList.add(cursor.getString(3));
            queryList.add(rowList);

            cursor.moveToNext();
        }
        return queryList;
    }
    // TODO Store location information in SQLite database
}
*/