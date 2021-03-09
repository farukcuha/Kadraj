package com.example.kadraj.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadraj.Models.NewsCategoryModel;
import com.example.kadraj.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    private ImageButton button;
    private ChipGroup chipGroup;
    private NewsAddingAdapter adapter;
    private List<NewsCategoryModel> list;
    AlertDialog.Builder builder;
    private List<NewsCategoryModel> journalList, sportList, technologyList;

    RecyclerView journalRecyclerView, sportRecyclerView, technologyRecyclerView;
    ChipGroup newsChipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings, container, false);

        button = view.findViewById(R.id.newsaddingbutton);
        chipGroup = view.findViewById(R.id.newschipgroup);

        journalList = new ArrayList<>();
        sportList = new ArrayList<>();
        technologyList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.newsaddingdialogitem, null);

                journalRecyclerView = dialogView.findViewById(R.id.journallist);
                sportRecyclerView = dialogView.findViewById(R.id.sportlist);
                technologyRecyclerView = dialogView.findViewById(R.id.technologylist);
                newsChipGroup = dialogView.findViewById(R.id.newschipgroup);

                journal(journalRecyclerView).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                sport(sportRecyclerView).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                technology(technologyRecyclerView).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                builder.setView(dialogView);
                builder.create().show();

            }
        });

        return view;
    }

    private RecyclerView journal(RecyclerView recyclerView){
        newsAdder(journalList, "Sözcü", R.drawable.sozcu, "https://www.sozcu.com.tr");
        newsAdder(journalList, "Sabah", R.drawable.sabahlogo, "https://www.sabah.com.tr");
        newsAdder(journalList, "Habertürk",  R.drawable.haberturk, "https://www.haberturk.com");
        newsAdder(journalList, "Hürriyet", R.drawable.hurriyet, "https://www.hurriyet.com.tr");
        newsAdder(journalList, "Karar", R.drawable.karar, "https://www.karar.com");
        newsAdder(journalList, "Milliyet", R.drawable.milliyet, "https://www.milliyet.com.tr");
        newsAdder(journalList, "Yeni Şafak", R.drawable.yenisafak, "https://www.yenisafak.com");
        newsAdder(journalList, "Türkiye", R.drawable.turkiye, "https://www.turkiyegazetesi.com.tr");
        newsAdder(journalList, "Takvim", R.drawable.takvim, "https://www.takvim.com.tr");
        newsAdder(journalList, "Yeni Akit", R.drawable.akit, "https://www.yeniakit.com.tr");

        adapter = new NewsAddingAdapter(journalList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;
    }

    private RecyclerView sport(RecyclerView recyclerView){
        newsAdder(sportList, "Bein Sports", R.drawable.turkiye, "https://tr.beinsports.com");
        newsAdder(sportList, "Fotomaç", R.drawable.sozcu, "https://www.fotomac.com.tr");
        newsAdder(sportList, "Fanatik", R.drawable.sabahlogo, "https://www.fanatik.com.tr");
        newsAdder(sportList, "SporX",  R.drawable.haberturk, "https://www.sporx.com");
        newsAdder(sportList, "A Spor", R.drawable.hurriyet, "https://www.aspor.com.tr");
        newsAdder(sportList, "Fotospor", R.drawable.karar, "https://www.fotospor.com");
        newsAdder(sportList, "NTV Spor", R.drawable.milliyet, "https://www.ntvspor.net");
        newsAdder(sportList, "TRT Spor", R.drawable.yenisafak, "https://www.trtspor.com.tr");
        newsAdder(sportList, "Ajans Spor", R.drawable.takvim, "https://ajansspor.com");

        adapter = new NewsAddingAdapter(sportList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;


    }

    private RecyclerView technology(RecyclerView recyclerView){
        newsAdder(technologyList, "Webtekno", R.drawable.sozcu, "https://www.webtekno.com");
        newsAdder(technologyList, "ShiftDelete", R.drawable.sabahlogo, "https://shiftdelete.net");
        newsAdder(technologyList, "Webrazzi",  R.drawable.haberturk, "https://webrazzi.com");
        newsAdder(technologyList, "Log", R.drawable.hurriyet, "https://www.log.com.tr");
        newsAdder(technologyList, "TeknolojiOku", R.drawable.karar, "https://www.teknolojioku.com");
        newsAdder(technologyList, "Chip", R.drawable.milliyet, "https://www.chip.com.tr");

        adapter = new NewsAddingAdapter(technologyList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;

    }

    private void newsAdder(List<NewsCategoryModel> list, String name, int image, String url){
        list.add(new NewsCategoryModel(name, image, url));
    }
}
