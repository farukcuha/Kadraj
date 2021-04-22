package com.example.kadraj.Tasks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.example.kadraj.Adapters.LocalNewsSliderAdapter;
import com.example.kadraj.CustomProgressDialog;
import com.example.kadraj.Models.SliderNewsModel;
import com.example.kadraj.SharedPreferencesProvider;
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
    private Dialog progressDialog;
    private FragmentManager fragmentManager;
    private Activity activity;

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

        localNewsSliderView.setSliderAdapter(new LocalNewsSliderAdapter(list, context, fragmentManager, activity, localNewsSliderView));
        new SharedPreferencesProvider(context).putLocalNewsData(list, "localnews");
        progressDialog.dismiss();

    }
}
