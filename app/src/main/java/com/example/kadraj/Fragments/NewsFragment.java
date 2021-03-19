package com.example.kadraj.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kadraj.Models.NewsCategoryModel;
import com.example.kadraj.NewsResourcesProvider;
import com.example.kadraj.Web.NewsWebView;
import com.example.kadraj.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import me.grantland.widget.AutofitHelper;


public class NewsFragment extends Fragment implements View.OnClickListener {
    private Fragment fragment;
    TextView news1, news2, news3, news4;
    List<NewsCategoryModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        news1 = view.findViewById(R.id.news1);
        news2 = view.findViewById(R.id.news2);
        news3 = view.findViewById(R.id.news3);
        news4 = view.findViewById(R.id.news4);




        list = new ArrayList<>();
        Set<String> src = PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("newschiplist", null);
        for (String s : src){
            List<NewsCategoryModel> comingList = new NewsResourcesProvider().getData(s);
            list.add(new NewsCategoryModel(
                    comingList.get(0).getNewsUrl(),
                    comingList.get(0).getNewsImage(),
                    comingList.get(0).getNewsName()
            ));
        }

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.newspagecontainer,
                new NewsWebView(list.get(0).getNewsUrl(), list.get(0).getNewsName())).commit();
        news1.setTextColor(Color.parseColor("#FF9A00"));

        news1.setText(list.get(0).getNewsName());
        news2.setText(list.get(1).getNewsName());
        news3.setText(list.get(2).getNewsName());
        news4.setText(list.get(3).getNewsName());

        news1.setOnClickListener(this);
        news2.setOnClickListener(this);
        news3.setOnClickListener(this);
        news4.setOnClickListener(this);

        AutofitHelper.create(news1);
        AutofitHelper.create(news2);
        AutofitHelper.create(news3);
        AutofitHelper.create(news4);
        return view;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news1:
                attachFragment(0, news1);
                break;
            case R.id.news2:
                attachFragment(1, news2);
                break;
            case R.id.news3:
                attachFragment(2, news3);
                break;
            case R.id.news4:
                attachFragment(3, news4);
                break;

        }
    }
    private void attachFragment(int position, TextView selected){
        news1.setTextColor(Color.WHITE);
        news2.setTextColor(Color.WHITE);
        news3.setTextColor(Color.WHITE);
        news4.setTextColor(Color.WHITE);

        selected.setTextColor(Color.parseColor("#FF9A00"));
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.newspagecontainer,
                new NewsWebView(list.get(position).getNewsUrl(), list.get(position).getNewsName())).commit();
    }
}