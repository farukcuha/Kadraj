package com.example.kadraj.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.kadraj.Adapters.LocalNewsSliderAdapter;
import com.example.kadraj.R;
import com.example.kadraj.SharedPreferencesProvider;
import com.example.kadraj.Tasks.CovidDatasTask;
import com.example.kadraj.Tasks.CurrencyPricesTask;
import com.example.kadraj.Tasks.LocalNewsTask;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;


public class HomepageFragment extends Fragment   {
    private ImageView weatherImage;
    private ProgressBar weatherProgressBar;
    private SliderView sliderView;
    private int currentPosition;
    String selectedLocalNewsLocation;
    private Button settingsButton;
    private TextView localNewsLocation, view_weatherLocation;
    private View view;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, container, false);

        settingsButton     = view.findViewById(R.id.bottomsettingsbutton);
        sliderView         = view.findViewById(R.id.localnewsslider);
        weatherImage       = view.findViewById(R.id.weatherimage);
        weatherProgressBar = view.findViewById(R.id.weatherprogressbar);
        localNewsLocation  = view.findViewById(R.id.location);
        view_weatherLocation    = view.findViewById(R.id.weatherlocationn);

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("kadrajcloud", Context.MODE_PRIVATE);
        selectedLocalNewsLocation = sharedPreferences.getString("localnewslocationname", "null");
        sharedPreferences.edit().remove("popularauthors").apply();

        new CurrencyPricesTask(getContext(), view).execute();
        new CovidDatasTask(getContext(), view).execute();

        localNewsLocationControl();
        loadWeatherImage(view);

        String resources = sharedPreferences.getString("localnews", "null");

        if (!resources.equals("null")){
            sliderView.setSliderAdapter(new LocalNewsSliderAdapter(
                    new SharedPreferencesProvider(getContext()).getLocalNewsData(resources, "localnews"),
                    getContext(),
                    getFragmentManager(),
                    getActivity(),
                    sliderView
            ));
        }
        else {
            if (selectedLocalNewsLocation.equals("null")){
                new LocalNewsTask(getContext(), sliderView, "https://www.hurriyet.com.tr/mersin-haberleri/", getFragmentManager(), getActivity()).execute();
            }
            else {
                String location = selectedLocalNewsLocation.toLowerCase()
                        .replace("ğ", "g")
                        .replace("ı", "i")
                        .replace("ç", "c")
                        .replace("ö", "o")
                        .replace("ş", "s")
                        .replace("ü", "u");

                new LocalNewsTask(getContext(), sliderView, "https://www.hurriyet.com.tr/"+location+"-haberleri/", getFragmentManager(), getActivity()).execute();
            }
        }
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragmentcontainer, new SettingsFragment()).commit();
            }
        });
        return view;
    }

    private void localNewsLocationControl() {
        if (selectedLocalNewsLocation.equals("null")){
            localNewsLocation.setText("Mersin");
        }
        else {
            localNewsLocation.setText(selectedLocalNewsLocation);
        }
    }

    private void loadWeatherImage(View view) {
        String weatherDistrictLocation = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("weatherdistrictsname", "null");
        view_weatherLocation.setText(weatherDistrictLocation);
        if (!weatherDistrictLocation.equals("null")){
            String convertedLocaion = weatherDistrictLocation.toUpperCase()
                    .replace("Ğ", "G")
                    .replace("İ", "I")
                    .replace("Ç", "C")
                    .replace("Ö", "O")
                    .replace("Ü", "U")
                    .replace("Ş", "S")
                    .replace(" ", "")
                    .replace("/", "-");

            Glide.with(view.getContext()).load("https://www.mgm.gov.tr/sunum/tahmin-show-2.aspx?m="+ convertedLocaion +"&basla=1&bitir=4&rC=fff&rZ=fff")
                    .centerCrop().fitCenter().listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    weatherProgressBar.setVisibility(View.VISIBLE);
                    return false;
                }
                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    weatherProgressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(weatherImage);
        }
        else {
            view_weatherLocation.setText("Bozyazı");
            Glide.with(view.getContext()).load("https://www.mgm.gov.tr/sunum/tahmin-show-2.aspx?m=BOZYAZI&basla=1&bitir=4&rC=fff&rZ=fff")
                    .centerCrop().fitCenter().listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    weatherProgressBar.setVisibility(View.VISIBLE);
                    return false;
                }
                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    weatherProgressBar.setVisibility(View.GONE);
                    return false;
                }
            }).into(weatherImage);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        currentPosition = sliderView.getCurrentPagePosition();
        Log.d("a", String.valueOf(currentPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        localNewsLocationControl();
        sliderView.setCurrentPagePosition(currentPosition);
    }




}