package com.pandorina.kadraj.Tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.fragment.app.FragmentManager;

import com.pandorina.kadraj.Adapters.LocalNewsSliderAdapter;
import com.pandorina.kadraj.Dialogs.CustomProgressDialog;
import com.pandorina.kadraj.Models.SliderNewsModel;
import com.pandorina.kadraj.SharedPreferencesProvider;
import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalNewsTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    @SuppressLint("StaticFieldLeak")
    SliderView localNewsSliderView;
    List<SliderNewsModel> list;
    String url;
    Dialog progressDialog;
    FragmentManager fragmentManager;
    @SuppressLint("StaticFieldLeak")
    Activity activity;

    public LocalNewsTask(Context context, SliderView localNewsSliderView, String url, FragmentManager fragmentManager, Activity activity) {
        this.context = context;
        this.localNewsSliderView = localNewsSliderView;
        this.url = url;
        this.fragmentManager = fragmentManager;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new CustomProgressDialog(context).loadingDialog();
        progressDialog.show();
        list = new ArrayList<>();

    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect(url).ignoreContentType(true).get();
            for (Element element : document.select("div[class=swiper-slide  read-count-container sponsored-news no-sponsor-text] > a")){
                String image = element.select("img").attr("data-src");

                if (image == null){
                    image = element.select("div[class=local-spot-colorbox] > img").attr("src");
                }
                else {
                    image = element.select("img").attr("data-src");
                }

                list.add(new SliderNewsModel(
                        image,
                        "https://www.hurriyet.com.tr" + element.attr("href"),
                        element.attr("title")
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        localNewsSliderView.setSliderAdapter(new LocalNewsSliderAdapter(list, context, fragmentManager, activity, localNewsSliderView));
        new SharedPreferencesProvider(context).putLocalNewsData(list, "localnews");
        progressDialog.dismiss();

    }
}
