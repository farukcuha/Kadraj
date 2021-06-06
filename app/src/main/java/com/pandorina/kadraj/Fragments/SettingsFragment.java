package com.pandorina.kadraj.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pandorina.kadraj.Adapters.NewsAddingAdapter;
import com.pandorina.kadraj.Database.DatabaseAccess;
import com.pandorina.kadraj.Models.NewsCategoryModel;
import com.pandorina.kadraj.NewsResourcesProvider;
import com.pandorina.kadraj.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SettingsFragment extends Fragment {
    ImageButton button;
    ChipGroup chipGroup;
    NewsAddingAdapter adapter;
    AlertDialog.Builder builder;
    List<NewsCategoryModel> journalList, sportList, technologyList;
    Spinner localNewsProvinces, weatherProvinces, weatherDistricts;
    String selectedDistrict;

    RecyclerView journalRecyclerView, sportRecyclerView, technologyRecyclerView;
    ChipGroup dialogChipGroup;
    NewsResourcesProvider provider = new NewsResourcesProvider();
    SharedPreferences sharedPreferences;
    TextView developer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings, container, false);

        button             = view.findViewById(R.id.newsaddingbutton);
        chipGroup          = view.findViewById(R.id.newschipgroup);
        localNewsProvinces = view.findViewById(R.id.localnewsspinnerprovinces);
        weatherProvinces   = view.findViewById(R.id.weatherspinnerprovinces);
        weatherDistricts   = view.findViewById(R.id.weatherspinnerdistricts);
        developer          = view.findViewById(R.id.developer);

        journalList     = new ArrayList<>();
        sportList       = new ArrayList<>();
        technologyList  = new ArrayList<>();

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("kadrajcloud", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("localnews").apply();
        setUpChipGroup();

        int defaultPreferences = sharedPreferences.getInt("localnewslocationposition", 0);
        localNewsProvinces.setSelection(defaultPreferences);

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.fragmentcontainer, new DeveloperFragment())
                        .addToBackStack("back")
                        .commit();
            }
        });

        localNewsProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferences.edit().putString("localnewslocationname", parent.getSelectedItem().toString()).apply();
                sharedPreferences.edit().putInt("localnewslocationposition", parent.getSelectedItemPosition()).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weatherProvinces.setSelection(sharedPreferences.getInt("weatherprovincesposition", 0));

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

                sharedPreferences.edit().putInt("weatherprovincesposition", parent.getSelectedItemPosition()).apply();
                sharedPreferences.edit().putString("weatherprovincesname", parent.getSelectedItem().toString()).apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        weatherDistricts.setSelection(sharedPreferences.getInt("weatherdistrictsposition", 0));

        weatherDistricts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = parent.getSelectedItem().toString();
                sharedPreferences.edit().putString("weatherlocation", selectedLocation).apply();

                if (selectedLocation.length() < 6){
                    sharedPreferences.edit().putString("weatherdistrictsname", selectedLocation).apply();

                }
                else {
                    char[] c = new char[6];
                    selectedLocation.getChars(selectedLocation.length() - 6, selectedLocation.length() ,c, 0);
                    Log.d("abcdefg", String.valueOf(c));

                    if (String.valueOf(c).equals("Merkez")){
                        char[] c2 = new char[selectedLocation.length() - 6];
                        selectedLocation.getChars(0, selectedLocation.length() - 6, c2, 0);
                        sharedPreferences.edit().putString("weatherdistrictsname", String.valueOf(c2)).apply();


                    }
                    else {
                        sharedPreferences.edit().putString("weatherdistrictsname", parent.getSelectedItem().toString()).apply();
                    }
                }

                sharedPreferences.edit().putInt("weatherdistrictsposition", parent.getSelectedItemPosition()).apply();

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
                        if (sharedPreferences.getStringSet("newschiplist", null).size() == 4){
                            chipGroup.removeAllViews();
                            setUpChipGroup();
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
        newsAdder(journalList, provider.getData("Sözcü"));
        newsAdder(journalList, provider.getData("Sabah"));
        newsAdder(journalList, provider.getData("Habertürk"));
        newsAdder(journalList, provider.getData("Hürriyet"));
        newsAdder(journalList, provider.getData("Karar"));
        newsAdder(journalList, provider.getData("Milliyet"));
        newsAdder(journalList, provider.getData("Yeni Şafak"));
        newsAdder(journalList, provider.getData("Türkiye"));
        newsAdder(journalList, provider.getData("Takvim"));
        newsAdder(journalList, provider.getData("Yeni Akit"));

        adapter = new NewsAddingAdapter(journalList, getContext(), dialogChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;
    }

    private RecyclerView sport(RecyclerView recyclerView, ChipGroup dialogChipGroup){
        newsAdder(sportList, provider.getData("Bein Sports"));
        newsAdder(sportList, provider.getData("Fotomaç"));
        newsAdder(sportList, provider.getData("Fanatik"));
        newsAdder(sportList, provider.getData("SporX"));
        newsAdder(sportList, provider.getData("A Spor"));
        newsAdder(sportList, provider.getData("Fotospor"));
        newsAdder(sportList, provider.getData("NTV Spor"));
        newsAdder(sportList, provider.getData("TRT Spor"));
        newsAdder(sportList, provider.getData("Ajans Spor"));

        adapter = new NewsAddingAdapter(sportList, getContext(), dialogChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;

    }

    private RecyclerView technology(RecyclerView recyclerView, ChipGroup dialogChipGroup){
        newsAdder(technologyList, provider.getData("Webtekno"));
        newsAdder(technologyList, provider.getData("ShiftDelete"));
        newsAdder(technologyList, provider.getData("Log"));
        newsAdder(technologyList, provider.getData("Chip"));
        newsAdder(technologyList, provider.getData("Webrazzi"));
        newsAdder(technologyList, provider.getData("TeknolojiOku"));

        adapter = new NewsAddingAdapter(technologyList, getContext(), dialogChipGroup);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        return recyclerView;

    }

    private void newsAdder(List<NewsCategoryModel> list, List<NewsCategoryModel> childList){
        for (int i = 0; i < childList.size(); i++){
            list.add(new NewsCategoryModel(
                    childList.get(i).getNewsUrl(),
                    childList.get(i).getNewsImage(),
                    childList.get(i).getNewsName()
            ));
        }

    }

    private void setUpChipGroup(){
        Set<String> set = sharedPreferences.getStringSet("newschiplist", null);
            if (set != null){
                for (String s : set){
                    Chip chip = (Chip) LayoutInflater.from(getContext()).inflate(R.layout.chip_item_default, chipGroup, false);
                    chip.setText(s);
                    chipGroup.addView(chip);

                }
            }
    }
}
