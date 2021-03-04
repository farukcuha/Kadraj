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
import com.example.kadraj.R;
import com.example.kadraj.Tasks.LocalNewsTask;
import com.smarteist.autoimageslider.SliderView;


public class HomepageFragment extends Fragment {
    private TextView cucumberPrice, eggplantPrice, beanPrice, pepperPrice1, pepperPrice2, tomatoPrice;
    private TextView goldPrice, dollarPrice, euroPrice, bitcoinPrice, ethereumPrice;
    private TextView goldChanging, dollarChanging, euroChanging, bitcoinChanging, ethereumChanging;
    private TextView todayTestNumber, todayCaseNumber, todayPatientNumber, todayDeathNumber, todayHealingNumber;
    private TextView allTestNumber, allCaseNumber, allHeavyPatientNumber, allDeathNumber, allHealingNumber;
    private ImageView weatherImage;
    private ProgressBar weatherProgressBar;
    private SliderView localNewsSliderView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("popularauthors").apply();

        new LocalNewsTask(getContext(), view.findViewById(R.id.localnewsslider), "https://www.hurriyet.com.tr/antalya-haberleri/").execute();

        idPairs(view);
        loadWeatherImage(view);

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

        weatherImage = view.findViewById(R.id.weatherimage);
        weatherProgressBar = view.findViewById(R.id.weatherprogressbar);

        todayTestNumber = view.findViewById(R.id.todaycasenumber);
        todayCaseNumber = view.findViewById(R.id.todaytestnumber);
        todayPatientNumber = view.findViewById(R.id.todaypatientnumber);
        todayDeathNumber = view.findViewById(R.id.todaydeathnumber);
        todayHealingNumber = view.findViewById(R.id.todayhealingnumber);
        allTestNumber = view.findViewById(R.id.alltestnumber);
        allCaseNumber = view.findViewById(R.id.allcasenumber);
        allHeavyPatientNumber = view.findViewById(R.id.allheavypatientnumber);
        allDeathNumber = view.findViewById(R.id.alldeathnumber);
        allHealingNumber = view.findViewById(R.id.allhealingnumber);

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
}