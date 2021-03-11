package com.example.kadraj.Fragments;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadraj.DatabaseAccess;
import com.example.kadraj.Models.NewsCategoryModel;
import com.example.kadraj.R;
import com.google.android.material.chip.ChipGroup;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {
    private ImageButton button;
    private ChipGroup chipGroup;
    private NewsAddingAdapter adapter;
    private List<String> list;
    AlertDialog.Builder builder;
    private List<NewsCategoryModel> journalList, sportList, technologyList;
    private Spinner localNewsProvinces, weatherProvinces, weatherDistricts;
    URL resourceId;

    RecyclerView journalRecyclerView, sportRecyclerView, technologyRecyclerView;
    ChipGroup newsChipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings, container, false);

        button = view.findViewById(R.id.newsaddingbutton);
        chipGroup = view.findViewById(R.id.newschipgroup);
        localNewsProvinces = view.findViewById(R.id.localnewsspinnerprovinces);
        weatherProvinces = view.findViewById(R.id.weatherspinnerprovinces);
        weatherDistricts = view.findViewById(R.id.weatherspinnerdistricts);

        journalList = new ArrayList<>();
        sportList = new ArrayList<>();
        technologyList = new ArrayList<>();

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();
        Cursor cursor = databaseAccess.getData();
        List<String> provincesList = new ArrayList<>();

        while (cursor.moveToNext()){
            Log.d("a", cursor.getString(0));
            provincesList.add(cursor.getString(0));
        }
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, provincesList);
        localNewsProvinces.setAdapter(adapter);




        localNewsProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weatherProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.newsaddingdialogitem, null);

                journalRecyclerView = dialogView.findViewById(R.id.journallist);
                sportRecyclerView = dialogView.findViewById(R.id.sportlist);
                technologyRecyclerView = dialogView.findViewById(R.id.technologylist);
                newsChipGroup = dialogView.findViewById(R.id.dialogchipgroup);

                journal(journalRecyclerView, newsChipGroup).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                sport(sportRecyclerView, newsChipGroup).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                technology(technologyRecyclerView, newsChipGroup).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                builder.setView(dialogView);
                builder.create().show();



            }
        });

        return view;
    }

    private RecyclerView journal(RecyclerView recyclerView, ChipGroup newsChipGroup){
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

        adapter = new NewsAddingAdapter(journalList, getContext(), newsChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;
    }

    private RecyclerView sport(RecyclerView recyclerView, ChipGroup newsChipGroup){
        newsAdder(sportList, "Bein Sports", R.drawable.bein, "https://tr.beinsports.com");
        newsAdder(sportList, "Fotomaç", R.drawable.fotomac, "https://www.fotomac.com.tr");
        newsAdder(sportList, "Fanatik", R.drawable.fanatik, "https://www.fanatik.com.tr");
        newsAdder(sportList, "SporX",  R.drawable.sporx, "https://www.sporx.com");
        newsAdder(sportList, "A Spor", R.drawable.aspor, "https://www.aspor.com.tr");
        newsAdder(sportList, "Fotospor", R.drawable.fotospor, "https://www.fotospor.com");
        newsAdder(sportList, "NTV Spor", R.drawable.ntvspor, "https://www.ntvspor.net");
        newsAdder(sportList, "TRT Spor", R.drawable.trtspor, "https://www.trtspor.com.tr");
        newsAdder(sportList, "Ajans Spor", R.drawable.ajansspor, "https://ajansspor.com");

        adapter = new NewsAddingAdapter(sportList, getContext(), newsChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;

    }

    private RecyclerView technology(RecyclerView recyclerView, ChipGroup newsChipGroup){
        newsAdder(technologyList, "Webtekno", R.drawable.webtekno, "https://www.webtekno.com");
        newsAdder(technologyList, "ShiftDelete", R.drawable.shitdelete, "https://shiftdelete.net");
        newsAdder(technologyList, "Log", R.drawable.log, "https://www.log.com.tr");
        newsAdder(technologyList, "Chip", R.drawable.chip, "https://www.chip.com.tr");
        newsAdder(technologyList, "Webrazzi",  R.drawable.webrazzi, "https://webrazzi.com");
        newsAdder(technologyList, "TeknolojiOku", R.drawable.teknolojioku, "https://www.teknolojioku.com");

        adapter = new NewsAddingAdapter(technologyList, getContext(), newsChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;

    }

    private void newsAdder(List<NewsCategoryModel> list, String name, int image, String url){
        list.add(new NewsCategoryModel(name, image, url));
    }
}
