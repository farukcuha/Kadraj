package com.example.kadraj.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

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
    public List<String> getData(String position){
        Cursor cursor = database.rawQuery("select baslik from ilceler where ilid" + " = '"+ position + "'", new String[] {});

        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(cursor.getString(0));
        }

        Log.d("dizisource", list.toString());

        return list;
    }



}
