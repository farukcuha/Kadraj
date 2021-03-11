package com.example.kadraj.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "ilceler.db.zip";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
}
