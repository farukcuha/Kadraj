package com.pandorina.kadraj.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandorina.kadraj.Models.NewsCategoryModel;
import com.pandorina.kadraj.NewsResourcesProvider;
import com.pandorina.kadraj.Web.NewsWebView;
import com.pandorina.kadraj.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.grantland.widget.AutofitHelper;


public class NewsFragment extends Fragment implements View.OnClickListener {
    TextView news1, news2, news3, news4;
    List<NewsCategoryModel> list;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        sharedPreferences = getContext().getSharedPreferences("kadrajcloud", Context.MODE_PRIVATE);

        news1 = view.findViewById(R.id.news1);
        news2 = view.findViewById(R.id.news2);
        news3 = view.findViewById(R.id.news3);
        news4 = view.findViewById(R.id.news4);

        list = new ArrayList<>();
        if (sharedPreferences.getStringSet("newschiplist", null) != null){
            Set<String> src = sharedPreferences.getStringSet("newschiplist", null);
            for (String s : src){
                List<NewsCategoryModel> comingList = new NewsResourcesProvider().getData(s);
                list.add(new NewsCategoryModel(
                        comingList.get(0).getNewsUrl(),
                        comingList.get(0).getNewsImage(),
                        comingList.get(0).getNewsName()
                ));
            }
        }
        defaultNews(list, "Sabah", R.drawable.sabahlogo, "https://www.sabah.com.tr");
        defaultNews(list, "Sözcü", R.drawable.sozcu, "https://www.sozcu.com.tr");
        defaultNews(list, "Habertürk", R.drawable.haberturk, "https://www.haberturk.com");
        defaultNews(list, "Webtekno", R.drawable.webtekno, "https://www.webtekno.com");

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

    private void defaultNews(List<NewsCategoryModel> list, String url, int image, String name){
        list.add(new NewsCategoryModel(url, image, name));
    }

}