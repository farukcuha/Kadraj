package com.example.kadraj.Fragments;

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
import com.example.kadraj.R;
import com.example.kadraj.Tasks.CurrencyPricesTask;
import com.example.kadraj.Tasks.LocalNewsTask;
import com.example.kadraj.Tasks.VegetablesPricesTask;
import com.smarteist.autoimageslider.SliderView;


public class HomepageFragment extends Fragment   {
    private ImageView weatherImage;
    private ProgressBar weatherProgressBar;
    private SliderView sliderView;
    private int currentPosition;

    private Button settingsButton;
    private TextView localNewsLocation;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, container, false);

        settingsButton     = view.findViewById(R.id.bottomsettingsbutton);
        sliderView         = view.findViewById(R.id.localnewsslider);
        weatherImage       = view.findViewById(R.id.weatherimage);
        weatherProgressBar = view.findViewById(R.id.weatherprogressbar);
        localNewsLocation  = view.findViewById(R.id.location);

        loadWeatherImage(view);

        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("localnews").apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("popularauthors").apply();
        new VegetablesPricesTask(getContext(), view).execute();
        new CurrencyPricesTask(getContext(), view).execute();



        /*String resources = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("localnews", "null");
        if (!resources.equals("null")){
            sliderView.setSliderAdapter(new LocalNewsSliderAdapter(
                    new SharedPreferencesProvider(getContext()).getLocalNewsData(resources, "localnews"),
                    getContext(),
                    getFragmentManager(),
                    getActivity(),
                    sliderView
            ));
        }*/
        //else {
            String selectedLocalNewsLocation = PreferenceManager
                    .getDefaultSharedPreferences(getContext()).getString("localnewslocationforurl", "null");
            Log.d("a", selectedLocalNewsLocation);

            if (selectedLocalNewsLocation.equals("null")){
                new LocalNewsTask(getContext(), sliderView, "https://www.hurriyet.com.tr/mersin-haberleri/", getFragmentManager(), getActivity()).execute();

            }
            else {
                new LocalNewsTask(getContext(), sliderView, "https://www.hurriyet.com.tr/"+selectedLocalNewsLocation+"-haberleri/", getFragmentManager(), getActivity()).execute();

            }
            localNewsLocation.setText(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("localnewslocationfortext", "null"));
        //}




        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new SettingsFragment()).commit();
            }
        });

        return view;
    }

    private void loadWeatherImage(View view) {

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

    @Override
    public void onPause() {
        super.onPause();
        currentPosition = sliderView.getCurrentPagePosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderView.setCurrentPagePosition(currentPosition);

    }

}