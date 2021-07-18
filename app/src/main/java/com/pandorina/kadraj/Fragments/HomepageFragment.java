package com.pandorina.kadraj.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pandorina.kadraj.Adapters.LocalNewsSliderAdapter;
import com.pandorina.kadraj.Dialogs.ErrorDialog;
import com.pandorina.kadraj.Dialogs.GoingToSettingsDialog;
import com.pandorina.kadraj.R;
import com.pandorina.kadraj.SharedPreferencesProvider;
import com.pandorina.kadraj.Tasks.CurrencyPricesTask;
import com.pandorina.kadraj.Tasks.LocalNewsTask;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class HomepageFragment extends Fragment   {
    ImageView weatherImage;
    ProgressBar weatherProgressBar;
    SliderView sliderView;
    int currentPosition;
    String selectedLocalNewsLocation;
    Button settingsButton;
    TextView localNewsLocation, view_weatherLocation;
    View view;
    SharedPreferences sharedPreferences;
    String resources;
    ErrorDialog errorDialog;
    LinearLayout weatherLayout, covidLayout, currencyLayout;
    GoingToSettingsDialog goingToSettingsDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, container, false);

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NotNull InitializationStatus initializationStatus) {

            }
        });

        settingsButton         = view.findViewById(R.id.bottomsettingsbutton);
        sliderView             = view.findViewById(R.id.localnewsslider);
        weatherImage           = view.findViewById(R.id.weatherimage);
        weatherProgressBar     = view.findViewById(R.id.weatherprogressbar);
        localNewsLocation      = view.findViewById(R.id.location);
        view_weatherLocation   = view.findViewById(R.id.weatherlocationn);
        weatherLayout          = view.findViewById(R.id.weatherlayout);
        currencyLayout         = view.findViewById(R.id.currencylayout);

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("kadrajcloud", Context.MODE_PRIVATE);
        selectedLocalNewsLocation = sharedPreferences.getString("localnewslocationname", "null");
        sharedPreferences.edit().remove("popularauthors").apply();

        resources = sharedPreferences.getString("localnews", "null");

        errorDialog = new ErrorDialog(getContext(), "İnternet bağlantınızı kontrol ediniz.");
        goingToSettingsDialog = new GoingToSettingsDialog(getContext(), getFragmentManager());

        checkInternetConnection();
        checkCustomLocation();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().addToBackStack("back").replace(R.id.fragmentcontainer, new SettingsFragment()).commit();
            }
        });

        return view;
    }

    private void checkCustomLocation() {
        if (sharedPreferences.getString("localnewslocationname", "null").equals("null") ||
                sharedPreferences.getString("weatherprovincesname", "null").equals("null")){
            goingToSettingsDialog.show();
        }
    }

    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = ((ConnectivityManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            new CurrencyPricesTask(getContext(), view).execute();
            getLocalNews();
            localNewsLocationControl();
            loadWeatherImage(view);

        } else {
            errorDialog.show();
        }
    }

    private void getLocalNews(){
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
            if (selectedLocalNewsLocation.equals("null") || selectedLocalNewsLocation.equals("İl Seçiniz")){
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
    }

    private void localNewsLocationControl() {
        if (selectedLocalNewsLocation.equals("null") || selectedLocalNewsLocation.equals("İl Seçiniz")){
            localNewsLocation.setText("Mersin");
        }
        else {
            localNewsLocation.setText(selectedLocalNewsLocation);
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadWeatherImage(View view) {
        String weatherDistrictLocation = sharedPreferences.getString("weatherdistrictsname", "null");
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
        if (sliderView.getSliderAdapter() != null){
            currentPosition = sliderView.getCurrentPagePosition();
        }
        
    }

    @Override
    public void onResume() {
        super.onResume();
        localNewsLocationControl();
        sliderView.setCurrentPagePosition(currentPosition);
    }




}