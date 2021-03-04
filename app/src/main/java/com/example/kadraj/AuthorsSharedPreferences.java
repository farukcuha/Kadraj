package com.example.kadraj;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.kadraj.Models.AuthorsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AuthorsSharedPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;


    public AuthorsSharedPreferences(Context context) {
        this.context = context;
    }

    public void putData(List<AuthorsModel> popularAuthorsList, String tag){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Gson gson = new Gson();
        String json = gson.toJson(popularAuthorsList);
        editor = sharedPreferences.edit();
        editor.putString(tag, json);
        editor.apply();
    }

    public List<AuthorsModel> getData(String resources, String tag){
        List<AuthorsModel> list;

        Gson gson = new Gson();
        Type type = new TypeToken<List<AuthorsModel>>(){}.getType();
        list = gson.fromJson(resources, type);

        Log.d(tag, "there are datas");

        return list;
    }
}
