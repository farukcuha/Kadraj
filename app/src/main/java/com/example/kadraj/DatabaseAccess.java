package com.example.kadraj;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    Cursor cursor = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);

        }
        return instance;
    }

    public void open(){
        this.database = openHelper.getReadableDatabase();

    }

    public void close(){
        if (database != null){
            this.database.close();
        }
    }

    public Cursor getData(){
        Cursor cursor = database.rawQuery("select baslik from iller", new String[] {});

        return cursor;
    }


}
