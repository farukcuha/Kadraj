package com.example.kadraj.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.kadraj.Tasks.CovidTask;
import com.example.kadraj.Tasks.LocalNewsTask;
import com.smarteist.autoimageslider.SliderView;


public class HomepageFragment extends Fragment {
    private TextView cucumberPrice, eggplantPrice, beanPrice, pepperPrice1, pepperPrice2, tomatoPrice;
    private TextView goldPrice, dollarPrice, euroPrice, bitcoinPrice, ethereumPrice;
    private TextView goldChanging, dollarChanging, euroChanging, bitcoinChanging, ethereumChanging;

    private ImageView weatherImage;
    private ProgressBar weatherProgressBar;
    private SliderView sliderView;
    private int currentPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);
        idPairs(view);
        loadWeatherImage(view);

        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("popularauthors").apply();

        String resources = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("localnews", "null");
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
            new LocalNewsTask(getContext(), sliderView, "https://www.hurriyet.com.tr/mersin-haberleri/", getFragmentManager(), getActivity()).execute();
        }

        new CovidTask(view, getContext()).execute();

        return view;
    }

    private void loadWeatherImage(View view) {
        Glide.with(view.getContext()).load("https://www.mgm.gov.tr/sunum/tahmin-show-2.aspx?m=USKUDAR&basla=1&bitir=4&rC=fff&rZ=fff")
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

    private void idPairs(View view) {
        sliderView = view.findViewById(R.id.localnewsslider);

        weatherImage = view.findViewById(R.id.weatherimage);
        weatherProgressBar = view.findViewById(R.id.weatherprogressbar);



        cucumberPrice = view.findViewById(R.id.cucumberprice);
        eggplantPrice = view.findViewById(R.id.eggplantprice);
        beanPrice = view.findViewById(R.id.beanprice);
        pepperPrice1 = view.findViewById(R.id.pepperprice1);
        pepperPrice2 = view.findViewById(R.id.pepperprice2);
        tomatoPrice = view.findViewById(R.id.tomatoprice);

        goldPrice = view.findViewById(R.id.goldprice);
        dollarPrice = view.findViewById(R.id.dollarprice);
        euroPrice = view.findViewById(R.id.europrice);
        bitcoinPrice = view.findViewById(R.id.bitcoinprice);
        ethereumPrice = view.findViewById(R.id.ethereumprice);

        goldChanging = view.findViewById(R.id.goldchanging);
        dollarChanging = view.findViewById(R.id.dollarchanging);
        euroChanging = view.findViewById(R.id.eurochanging);
        bitcoinChanging = view.findViewById(R.id.bitcoinchanging);
        ethereumChanging = view.findViewById(R.id.ethereumchanging);

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