package com.example.kadraj.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadraj.Database.DatabaseAccess;
import com.example.kadraj.Models.NewsCategoryModel;
import com.example.kadraj.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SettingsFragment extends Fragment {
    private ImageButton button;
    private ChipGroup chipGroup;
    private NewsAddingAdapter adapter;
    AlertDialog.Builder builder;
    private List<NewsCategoryModel> journalList, sportList, technologyList;
    private Spinner localNewsProvinces, weatherProvinces, weatherDistricts;
    String selectedDistrict;

    RecyclerView journalRecyclerView, sportRecyclerView, technologyRecyclerView;
    ChipGroup dialogChipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings, container, false);

        button             = view.findViewById(R.id.newsaddingbutton);
        chipGroup          = view.findViewById(R.id.newschipgroup);
        localNewsProvinces = view.findViewById(R.id.localnewsspinnerprovinces);
        weatherProvinces   = view.findViewById(R.id.weatherspinnerprovinces);
        weatherDistricts   = view.findViewById(R.id.weatherspinnerdistricts);

        journalList     = new ArrayList<>();
        sportList       = new ArrayList<>();
        technologyList  = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        setUpChipGroup();

        int defaultPreferences = preferences.getInt("localnewslocationposition", 0);
        localNewsProvinces.setSelection(defaultPreferences);

        localNewsProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                preferences.edit().putString("localnewslocationname", parent.getSelectedItem().toString()).apply();
                preferences.edit().putInt("localnewslocationposition", parent.getSelectedItemPosition()).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weatherProvinces.setSelection(preferences.getInt("weatherprovincesposition", 0));

        weatherProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("a", String.valueOf(parent.getSelectedItemPosition()));
                selectedDistrict = String.valueOf(parent.getSelectedItemPosition());

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
                databaseAccess.open();
                ArrayAdapter<String> adapter  = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        databaseAccess.getData(selectedDistrict));
                weatherDistricts.setAdapter(adapter);
                databaseAccess.close();

                preferences.edit().putInt("weatherprovincesposition", parent.getSelectedItemPosition()).apply();
                preferences.edit().putString("weatherprovincesname", parent.getSelectedItem().toString()).apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weatherDistricts.setSelection(preferences.getInt("weatherdistrictsposition", 0));

        weatherDistricts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = parent.getSelectedItem().toString();
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("weatherlocation", selectedLocation).apply();

                if (selectedLocation.length() < 6){
                    preferences.edit().putString("weatherdistrictsname", selectedLocation).apply();

                }
                else {
                    char[] c = new char[6];
                    selectedLocation.getChars(selectedLocation.length() - 6, selectedLocation.length() ,c, 0);
                    Log.d("abcdefg", String.valueOf(c));

                    if (String.valueOf(c).equals("Merkez")){
                        char[] c2 = new char[selectedLocation.length() - 6];
                        selectedLocation.getChars(0, selectedLocation.length() - 6, c2, 0);
                        preferences.edit().putString("weatherdistrictsname", String.valueOf(c2)).apply();


                    }
                    else {
                        preferences.edit().putString("weatherdistrictsname", parent.getSelectedItem().toString()).apply();
                    }
                }

                preferences.edit().putInt("weatherdistrictsposition", parent.getSelectedItemPosition()).apply();

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

                journalRecyclerView    = dialogView.findViewById(R.id.journallist);
                sportRecyclerView      = dialogView.findViewById(R.id.sportlist);
                technologyRecyclerView = dialogView.findViewById(R.id.technologylist);
                dialogChipGroup = dialogView.findViewById(R.id.dialogchipgroup);

                journal(journalRecyclerView, dialogChipGroup).setLayoutManager(
                        new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
                sport(sportRecyclerView, dialogChipGroup).setLayoutManager(
                        new LinearLayoutManager(getContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false));
                technology(technologyRecyclerView, dialogChipGroup).setLayoutManager(
                        new LinearLayoutManager(getContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false));
                builder.setView(dialogView);
                builder.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("newschiplist", null).size() == 4){
                            chipGroup.removeAllViews();
                            setUpChipGroup();
                        }
                        else {
                            Toast.makeText(getContext(), "Olmadı", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

    private RecyclerView journal(RecyclerView recyclerView, ChipGroup dialogChipGroup){
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

        adapter = new NewsAddingAdapter(journalList, getContext(), dialogChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;
    }

    private RecyclerView sport(RecyclerView recyclerView, ChipGroup dialogChipGroup){
        newsAdder(sportList, "Bein Sports", R.drawable.bein, "https://tr.beinsports.com");
        newsAdder(sportList, "Fotomaç", R.drawable.fotomac, "https://www.fotomac.com.tr");
        newsAdder(sportList, "Fanatik", R.drawable.fanatik, "https://www.fanatik.com.tr");
        newsAdder(sportList, "SporX",  R.drawable.sporx, "https://www.sporx.com");
        newsAdder(sportList, "A Spor", R.drawable.aspor, "https://www.aspor.com.tr");
        newsAdder(sportList, "Fotospor", R.drawable.fotospor, "https://www.fotospor.com");
        newsAdder(sportList, "NTV Spor", R.drawable.ntvspor, "https://www.ntvspor.net");
        newsAdder(sportList, "TRT Spor", R.drawable.trtspor, "https://www.trtspor.com.tr");
        newsAdder(sportList, "Ajans Spor", R.drawable.ajansspor, "https://ajansspor.com");

        adapter = new NewsAddingAdapter(sportList, getContext(), dialogChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;

    }

    private RecyclerView technology(RecyclerView recyclerView, ChipGroup dialogChipGroup){
        newsAdder(technologyList, "Webtekno", R.drawable.webtekno, "https://www.webtekno.com");
        newsAdder(technologyList, "ShiftDelete", R.drawable.shitdelete, "https://shiftdelete.net");
        newsAdder(technologyList, "Log", R.drawable.log, "https://www.log.com.tr");
        newsAdder(technologyList, "Chip", R.drawable.chip, "https://www.chip.com.tr");
        newsAdder(technologyList, "Webrazzi",  R.drawable.webrazzi, "https://webrazzi.com");
        newsAdder(technologyList, "TeknolojiOku", R.drawable.teknolojioku, "https://www.teknolojioku.com");

        adapter = new NewsAddingAdapter(technologyList, getContext(), dialogChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;

    }

    private void newsAdder(List<NewsCategoryModel> list, String name, int image, String url){
        list.add(new NewsCategoryModel(name, image, url));
    }

    private void setUpChipGroup(){
        Set<String> set = PreferenceManager.getDefaultSharedPreferences(getContext()).getStringSet("newschiplist", null);
            if (set == null){
                Log.d("a", "bok");
            }
            else {
                for (String s : set){
                    Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.chip_item_default, chipGroup, false);
                    chip.setText(s);
                    chipGroup.addView(chip);
                    Log.d("aacas", "oldu");

                }
            }
    }

}
