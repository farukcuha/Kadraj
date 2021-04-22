package com.example.kadraj.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadraj.Database.DatabaseAccess;
import com.example.kadraj.Models.NewsCategoryModel;
import com.example.kadraj.NewsResourcesProvider;
import com.example.kadraj.R;
import com.example.kadraj.Web.NewsWebView;
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
    //ImageView google, github, linkedin;

    RecyclerView journalRecyclerView, sportRecyclerView, technologyRecyclerView;
    ChipGroup dialogChipGroup;
    NewsResourcesProvider provider = new NewsResourcesProvider();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings, container, false);

        button             = view.findViewById(R.id.newsaddingbutton);
        chipGroup          = view.findViewById(R.id.newschipgroup);
        localNewsProvinces = view.findViewById(R.id.localnewsspinnerprovinces);
        weatherProvinces   = view.findViewById(R.id.weatherspinnerprovinces);
        weatherDistricts   = view.findViewById(R.id.weatherspinnerdistricts);
        /*google = view.findViewById(R.id.google);
        github = view.findViewById(R.id.github);
        linkedin = view.findViewById(R.id.linkedin);

        google.setOnClickListener(this);
        github.setOnClickListener(this);
        linkedin.setOnClickListener(this);*/


        journalList     = new ArrayList<>();
        sportList       = new ArrayList<>();
        technologyList  = new ArrayList<>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("localnews").apply();
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

    /*@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.google:
                Intent intent = new Intent();
                intent.setType(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:ahmetfarukcuha@gmail.com"));
                startActivity(intent);
                break;
            case R.id.github:
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.fragmentcontainer, new NewsWebView("https://github.com/farukcuha", "github"))
                        .addToBackStack("TAG")
                        .commit();
                break;
            case R.id.linkedin:

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.fragmentcontainer, new NewsWebView("www.linkedin.com/in/ahmet-faruk-çuha-5a8209116", "linkedin"))
                        .addToBackStack("TAG")
                        .commit();
                break;
        }
    }*/
}
