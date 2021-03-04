package com.example.kadraj.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kadraj.Adapters.LocalNewsSliderAdapter;
import com.example.kadraj.CustomProgressDialog;
import com.example.kadraj.Models.SliderNewsModel;
import com.smarteist.autoimageslider.SliderView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalNewsTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private SliderView localNewsSliderView;
    private List<SliderNewsModel> list;
    private String url;

    public LocalNewsTask(Context context, SliderView localNewsSliderView, String url) {
        this.context = context;
        this.localNewsSliderView = localNewsSliderView;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        new CustomProgressDialog(context).loadingDialog().show();
        list = new ArrayList<>();

    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect(url).ignoreContentType(true).get();

            for (Element element : document.select("div[class=swiper-slide  read-count-container sponsored-news no-sponsor-text] > a")){
                list.add(new SliderNewsModel(
                        element.select("img").attr("data-src"),
                        element.attr("href"),
                        element.attr("title")
                ));
                Log.d("sdfg", element.select("img").attr("data-src"));
                Log.d("sdfg", element.attr("href"));
                Log.d("sdfg", element.select("div[class=slide-description]").text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        localNewsSliderView.setSliderAdapter(new LocalNewsSliderAdapter(list, context));
        new CustomProgressDialog(context).loadingDialog().dismiss();

    }
}
