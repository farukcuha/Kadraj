package com.example.kadraj.Web;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kadraj.R;


public class NewsWebView extends Fragment {
    private String name,url;


    public NewsWebView(String url, String name) {
        this.url = url;
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        new WebViewProvider(view, name, url, R.id.web_view_sabah, R.id.sabahprogress).provider();
        return view;
    }





}
