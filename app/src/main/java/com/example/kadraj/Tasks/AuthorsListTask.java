package com.example.kadraj.Tasks;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kadraj.Adapters.AuthorsAdapter;
import com.example.kadraj.SharedPreferencesProvider;
import com.example.kadraj.CustomProgressDialog;
import com.example.kadraj.Models.AuthorsModel;
import com.example.kadraj.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorsListTask extends AsyncTask<Void, Void, Void> {
    private String newspaperUrl;
    private Document document;
    private List<AuthorsModel> list;
    private Context context;
    private FragmentManager fragmentManager;
    private RecyclerView recyclerView;
    private Dialog progressDialog;

    public AuthorsListTask(String newspaperUrl, Context context, FragmentManager fragmentManager, RecyclerView recyclerView) {
        this.newspaperUrl = newspaperUrl;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
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
        switch (newspaperUrl){
            case "https://www.sabah.com.tr/yazarlar":
                sabah();
                break;
            case "https://www.haberturk.com/htyazarlar":
                haberturk();
                break;
            case "https://www.hurriyet.com.tr/yazarlar/":
                hurriyet();
                break;
            case "https://www.karar.com/yazarlar":
                karar();
                break;

            case "https://www.sozcu.com.tr/kategori/yazarlar/":
                sozcu();
                break;
            case "https://www.milliyet.com.tr/yazarlar/":
                milliyet();
                break;

            case "https://www.turkiyegazetesi.com.tr/yazarlar":
                turkiye();
                break;
            case "https://www.yenisafak.com/yazarlar/bugun-yazanlar":
                yenisafak();
                break;
            case "https://www.yeniakit.com.tr/yazarlar/":
                yeniakit();
                break;
            case "https://www.takvim.com.tr/yazarlar":
                takvim();
                break;


        }

        return null;
    }

    private void sabah(){
        try {

            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();
            for (Element element : document.select("figure[class=multiple boxShadowSet]")){

                list.add(new AuthorsModel(
                        element.select("h3[class=postTitle]").text(),
                        element.select("img[class=lazyload]").attr("data-src"),
                        element.select("strong[class=postCaption]").text(),
                        "https://www.sabah.com.tr" + element.select("figcaption > a").attr("href"),
                        R.drawable.sabahlogo,
                        "all"
                ));
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void haberturk() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("div[class=author type2]")){

                list.add(new AuthorsModel(
                        element.select("span[class=name]").text(),
                        element.select("div[class=author-info] > figure > img").attr("src"),
                        element.select("span.last-article-title").text(),
                        "https://www.haberturk.com"+element.select("div.info").parents().attr("href"),
                        R.drawable.haberturk,
                        "all"
                ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void hurriyet() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();
            for (int i = 0; i < 15;i++){
                Element element = document.select("a[class=author-box]").get(i);
                list.add(new AuthorsModel(
                        element.select("span.name").text(),
                        element.select("div.author-box-image > img").attr("src"),
                        element.select("span.title").text(),
                        "https://www.hurriyet.com.tr" + element.attr("href"),
                        R.drawable.hurriyet,
                        "all"

                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void karar() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("div[class=title line-camp line-2]")) {

                list.add(new AuthorsModel(
                        element.parent().select("div[class=author-name]").text(),
                        element.parent().select("img.lazy").attr("data-src"),
                        element.text(),
                        "https://www.karar.com"+element.parent().attr("href"),
                        R.drawable.karar,
                        "all"
                ));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void sozcu() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("div[class=cas-inner]")){

                String eskiresim = element.select("span[class=news-img]").attr("style");
                char[] yeniresim = new char[eskiresim.length()-20];
                eskiresim.getChars(21, eskiresim.length()-1, yeniresim,0);
                Log.d("a", String.valueOf(yeniresim));

                list.add(new AuthorsModel(
                        element.select("div[class=columnist-name]").text(),
                        String.valueOf(yeniresim),
                        element.select("div[class=c-text]").text(),
                        element.select("a").attr("href"),
                        R.drawable.sozcu,
                        "all"
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void milliyet() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("a[class=card-listing__link]")){
                list.add(new AuthorsModel(
                        element.select("span[class=card-listing__author-name]").text(),
                        element.select("img[class=card-listing__image]").attr("src"),
                        element.select("span[class=card-listing__title]").text(),
                        "https://www.milliyet.com.tr"+element.select("a").attr("href"),
                        R.drawable.milliyet,
                        "all"
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    private void turkiye() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("table[class=yazar-kutu]")){

                list.add(new AuthorsModel(
                        element.select("span[id=contentOrta_dl_yazarlar_Label1_0]").text(),
                        element.select("img[id=contentOrta_dl_yazarlar_img_yazar_0]").attr("src"),
                        element.select("span[id=contentOrta_dl_yazarlar_Label2_0]").text(),
                        element.select("tbody > tr > td > a").attr("href"),
                        R.drawable.milliyet,
                        "all"
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void yenisafak() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("a[class=article]")){
                String eskiAd = element.select("div[class=name]").text();
                char[] yeniAd = new char[eskiAd.length()-13];
                eskiAd.getChars(0,eskiAd.length()-13, yeniAd,0);
                Log.d("a", String.valueOf(yeniAd));

                list.add(new AuthorsModel(
                        String.valueOf(yeniAd),
                        element.select("div[class=photo circle] > img").attr("src"),
                        element.attr("title"),
                        "https://www.yenisafak.com"+element.attr("href"),
                        R.drawable.yenisafak,
                        "all"
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void yeniakit() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("p[class=postTitle]")){

                list.add(new AuthorsModel(
                        element.parent().select("p[class=authorName]").text(),
                        element.parent().parent().select("img[class=b-lazy]").attr("data-src"),
                        element.parent().select("p[class=postTitle]").text(),
                        element.parent().parent().select("a").attr("href"),
                        R.drawable.akit,
                        "all"
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void takvim() {
        try {
            document = Jsoup.connect(newspaperUrl).ignoreContentType(true).get();

            for (Element element : document.select("ul[class=list] > li")){

                list.add(new AuthorsModel(
                        element.select("ol > li[class=title] > a").text(),
                        element.select("figure > a > img").attr("src"),
                        element.select("ol > li[class=writing]").text(),
                        "https://www.takvim.com.tr"+element.select("figure > a").attr("href"),
                        R.drawable.takvim,
                        "all"
                ));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        AuthorsAdapter authorsAdapter = new AuthorsAdapter(list, context, fragmentManager);
        recyclerView.setAdapter(authorsAdapter);

        new SharedPreferencesProvider(context).putAuthorsData(list, "newspaperauthors");

        progressDialog.dismiss();
    }

}
