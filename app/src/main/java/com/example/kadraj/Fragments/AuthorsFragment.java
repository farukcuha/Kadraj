package com.example.kadraj.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kadraj.Adapters.AuthorsAdapter;
import com.example.kadraj.Adapters.PapernewsAdapter;
import com.example.kadraj.AuthorsSharedPreferences;
import com.example.kadraj.Models.PapernewsModel;
import com.example.kadraj.Tasks.PopularAuthorsTask;
import com.example.kadraj.R;

import java.util.ArrayList;
import java.util.List;


public class AuthorsFragment extends Fragment {
    private RecyclerView papernewsReyclerView, popularAuthorsRecyclerView;
    private PapernewsAdapter papernewsAdapter;
    private List<PapernewsModel> papernewsimages;
    private AuthorsAdapter popularAuthorsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);

        papernewsReyclerView = view.findViewById(R.id.papernewsrecyclerview);
        popularAuthorsRecyclerView = view.findViewById(R.id.popularauthors);
        setUpPapernewsList();
        setUpAuthorList();

        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("newspaperauthors").apply();

        return view;
    }

    private void setUpPapernewsList() {
        papernewsimages = new ArrayList<>();

        papernewsReyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

        papernewsReyclerView.setHasFixedSize(true);

        papernewsimages.add(new PapernewsModel(R.drawable.sabahlogo, "https://www.sabah.com.tr/yazarlar", "Sabah"));
        papernewsimages.add(new PapernewsModel(R.drawable.sozcu, "https://www.sozcu.com.tr/kategori/yazarlar/", "Sözcü"));
        papernewsimages.add(new PapernewsModel(R.drawable.karar, "https://www.karar.com/yazarlar", "Karar"));
        papernewsimages.add(new PapernewsModel(R.drawable.turkiye, "https://www.turkiyegazetesi.com.tr/yazarlar", "Türkiye"));
        papernewsimages.add(new PapernewsModel(R.drawable.takvim, "https://www.takvim.com.tr/yazarlar", "Takvim"));
        papernewsimages.add(new PapernewsModel(R.drawable.haberturk, "https://www.haberturk.com/htyazarlar", "Habertürk"));
        papernewsimages.add(new PapernewsModel(R.drawable.hurriyet, "https://www.hurriyet.com.tr/yazarlar/", "Hürriyet"));
        papernewsimages.add(new PapernewsModel(R.drawable.milliyet, "https://www.milliyet.com.tr/yazarlar/", "Milliyet"));
        papernewsimages.add(new PapernewsModel(R.drawable.yenisafak, "https://www.yenisafak.com/yazarlar/bugun-yazanlar", "Yenişafak"));
        papernewsimages.add(new PapernewsModel(R.drawable.akit, "https://www.yeniakit.com.tr/yazarlar/", "Yeni Akit"));

        papernewsAdapter = new PapernewsAdapter(papernewsimages, getContext(), getFragmentManager());
        papernewsReyclerView.setAdapter(papernewsAdapter);

    }
    private void setUpAuthorList() {
        String resources = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("popularauthors", "null");

        if (!resources.equals("null")){
            popularAuthorsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            popularAuthorsRecyclerView.setLayoutManager(linearLayoutManager);
            popularAuthorsAdapter = new AuthorsAdapter(new AuthorsSharedPreferences(getContext()).getData(resources, "popularauthors"), getContext(), getFragmentManager());
            popularAuthorsRecyclerView.setAdapter(popularAuthorsAdapter);
        }
        else {
            new PopularAuthorsTask(getContext(), popularAuthorsRecyclerView, getFragmentManager()).execute();
        }




    }
}