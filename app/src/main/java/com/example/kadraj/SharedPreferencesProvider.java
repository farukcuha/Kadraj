package com.example.kadraj;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.kadraj.Models.AuthorsModel;
import com.example.kadraj.Models.SliderNewsModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferencesProvider {
    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesProvider(Context context) {
        sharedPreferences = context.getSharedPreferences("kadrajcloud", Context.MODE_PRIVATE);
    }

    public void putAuthorsData(List<AuthorsModel> popularAuthorsList, String tag){

        Gson gson = new Gson();
        String json = gson.toJson(popularAuthorsList);
        editor = sharedPreferences.edit();
        editor.putString(tag, json);
        editor.apply();
    }

    public List<AuthorsModel> getAuthorsData(String resources, String tag){
        List<AuthorsModel> list;

        Gson gson = new Gson();
        Type type = new TypeToken<List<AuthorsModel>>(){}.getType();
        list = gson.fromJson(resources, type);

        return list;
    }

    public void putLocalNewsData(List<SliderNewsModel> popularAuthorsList, String tag){
        Gson gson = new Gson();
        String json = gson.toJson(popularAuthorsList);
        editor = sharedPreferences.edit();
        editor.putString(tag, json);
        editor.apply();
    }

    public List<SliderNewsModel> getLocalNewsData(String resources, String tag){
        List<SliderNewsModel> list;

        Gson gson = new Gson();
        Type type = new TypeToken<List<SliderNewsModel>>(){}.getType();
        list = gson.fromJson(resources, type);

        return list;
    }


}
