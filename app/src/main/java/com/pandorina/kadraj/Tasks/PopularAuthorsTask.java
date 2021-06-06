package com.pandorina.kadraj.Tasks;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pandorina.kadraj.Adapters.AuthorsAdapter;
import com.pandorina.kadraj.SharedPreferencesProvider;
import com.pandorina.kadraj.Dialogs.CustomProgressDialog;
import com.pandorina.kadraj.Models.AuthorsModel;
import com.pandorina.kadraj.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PopularAuthorsTask extends AsyncTask<Void, Void, Void> {
    AuthorsAdapter popularAuthorsAdapter;
    @SuppressLint("StaticFieldLeak")
    Context context;
    @SuppressLint("StaticFieldLeak")
    RecyclerView recyclerView;
    List<AuthorsModel> popularAuthorsList;
    Dialog progressDialog;
    Document document;
    FragmentManager fragmentManager;

    public PopularAuthorsTask(Context context, RecyclerView recyclerView, FragmentManager fragmentManager) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;

        popularAuthorsList = new ArrayList<>();
        popularAuthorsAdapter = new AuthorsAdapter(popularAuthorsList, context, fragmentManager);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new CustomProgressDialog(context).loadingDialog();
        progressDialog.show();
    }
    @Override
    protected Void doInBackground(Void... voids) {
        sabah();sozcu();karar();turkiye();
        takvim();haberturk();hurriyet();
        milliyet();yenisafak();akit();

        return null;
    }

    private void sabah() {
        try {
            document = Jsoup.connect("https://www.sabah.com.tr/yazarlar").ignoreContentType(true).get();

            for (int i = 0; i < 3; i++){
                Element element = document.select("figure[class=multiple boxShadowSet]").get(i);
                popularAuthorsList.add(new AuthorsModel(
                        element.select("h3[class=postTitle]").text(),
                        element.select("img[class=lazyload]").attr("data-src"),
                        element.select("strong[class=postCaption]").text(),
                        "https://www.sabah.com.tr" + element.select("figcaption > a").attr("href"),
                        R.drawable.sabahlogo,
                        "popular"
                ));
                popularAuthorsAdapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sozcu() {
        try {
            document = Jsoup.connect("https://www.sozcu.com.tr/kategori/yazarlar/").ignoreContentType(true).get();

            for (int i = 4; i > 1; i--){
                Element element = document.select("div[class=cas-inner]").get(i);

                String eskiresim = element.select("span[class=news-img]").attr("style");
                char[] yeniresim = new char[eskiresim.length() - 20];
                eskiresim.getChars(21, eskiresim.length() - 1, yeniresim, 0);
                Log.d("a", String.valueOf(yeniresim));

                popularAuthorsList.add(new AuthorsModel(
                        element.select("div[class=columnist-name]").text(),
                        String.valueOf(yeniresim),
                        element.select("div[class=c-text]").text(),
                        element.select("a").attr("href"),
                        R.drawable.sozcu,
                        "popular"
                ));
                popularAuthorsAdapter.notifyDataSetChanged();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void haberturk() {
        try {
            document = Jsoup.connect("https://www.haberturk.com/htyazarlar").ignoreContentType(true).get();

            Element element = document.select("div[class=author type2]").get(0);

            popularAuthorsList.add(new AuthorsModel(
                    element.select("span[class=name]").text(),
                    element.select("div[class=author-info] > figure > img").attr("src"),
                    element.select("span.last-article-title").text(),
                    "https://www.haberturk.com"+element.select("div.info").parents().attr("href"),
                    R.drawable.haberturk,
                    "popular"
            ));
            popularAuthorsAdapter.notifyDataSetChanged();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void karar() {
        try {
            document = Jsoup.connect("https://www.karar.com/yazarlar").ignoreContentType(true).get();
            Element element = document.select("div[class=title line-camp line-2]").get(0);

            popularAuthorsList.add(new AuthorsModel(
                    element.parent().select("div[class=author-name]").text(),
                    element.parent().select("img.lazy").attr("data-src"),
                    element.text(),
                    "https://www.karar.com"+element.parent().attr("href"),
                    R.drawable.karar,
                    "popular"
            ));
            popularAuthorsAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void turkiye() {
        try {
            document = Jsoup.connect("https://www.turkiyegazetesi.com.tr/yazarlar").ignoreContentType(true).get();

            Element element = document.select("table[class=yazar-kutu]").get(1);


                popularAuthorsList.add(new AuthorsModel(
                        element.select("span[id=contentOrta_dl_yazarlar_Label1_0]").text(),
                        element.select("img[id=contentOrta_dl_yazarlar_img_yazar_0]").attr("src"),
                        element.select("span[id=contentOrta_dl_yazarlar_Label2_0]").text(),
                        element.select("tbody > tr > td > a").attr("href"),
                        R.drawable.turkiye,
                        "popular"
                ));
            popularAuthorsAdapter.notifyDataSetChanged();
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
    private void takvim() {
        try {
            document = Jsoup.connect("https://www.takvim.com.tr/yazarlar").ignoreContentType(true).get();
            Element element = document.select("ul[class=list] > li").get(0);

            popularAuthorsList.add(new AuthorsModel(
                        element.select("ol > li[class=title] > a").text(),
                        element.select("figure > a > img").attr("src"),
                        element.select("ol > li[class=writing]").text(),
                        "https://www.takvim.com.tr"+element.select("figure > a").attr("href"),
                    R.drawable.takvim,
                    "popular"
                ));
            popularAuthorsAdapter.notifyDataSetChanged();
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    private void hurriyet() {
        try {
            document = Jsoup.connect("https://www.hurriyet.com.tr/yazarlar/").ignoreContentType(true).get();

            Element element = document.select("a[class=author-box]").get(0);

            popularAuthorsList.add(new AuthorsModel(
                        element.select("span.name").text(),
                        element.select("div.author-box-image > img").attr("src"),
                        element.select("span.title").text(),
                        "https://www.hurriyet.com.tr" + element.attr("href"),
                    R.drawable.hurriyet,
                    "popular"

                ));
            popularAuthorsAdapter.notifyDataSetChanged();
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void milliyet() {
        try {
            document = Jsoup.connect("https://www.milliyet.com.tr/yazarlar/").ignoreContentType(true).get();

            Element element = document.select("a[class=card-listing__link]").get(0);

            popularAuthorsList.add(new AuthorsModel(
                        element.select("span[class=card-listing__author-name]").text(),
                        element.select("img[class=card-listing__image]").attr("src"),
                        element.select("span[class=card-listing__title]").text(),
                        "https://www.milliyet.com.tr"+element.select("a").attr("href"),
                    R.drawable.milliyet,
                    "popular"
                ));
            popularAuthorsAdapter.notifyDataSetChanged();
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void yenisafak() {
        try {
            document = Jsoup.connect("https://www.yenisafak.com/yazarlar").ignoreContentType(true).get();
            Element element = document.select("a[class=article]").get(0);

            String eskiAd = element.select("div[class=name]").text();
            char[] yeniAd = new char[eskiAd.length()-13];
            eskiAd.getChars(0,eskiAd.length()-13, yeniAd,0);
            Log.d("a", String.valueOf(yeniAd));

                popularAuthorsList.add(new AuthorsModel(
                        String.valueOf(yeniAd),
                        element.select("div[class=photo circle] > img").attr("src"),
                        element.attr("title"),
                        "https://www.yenisafak.com"+element.attr("href"),
                        R.drawable.yenisafak,
                        "popular"
                ));
            popularAuthorsAdapter.notifyDataSetChanged();
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void akit() {
        try {
            document = Jsoup.connect("https://www.yeniakit.com.tr/yazarlar/").ignoreContentType(true).get();

            Element element = document.select("p[class=postTitle]").get(0);
            popularAuthorsList.add(new AuthorsModel(
                    element.parent().select("p[class=authorName]").text(),
                    element.parent().parent().select("img[class=b-lazy]").attr("data-src"),
                    element.parent().select("p[class=postTitle]").text(),
                    element.parent().parent().select("a").attr("href"),
                    R.drawable.akit,
                    "popular"
            ));
            popularAuthorsAdapter.notifyDataSetChanged();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(popularAuthorsAdapter);
        new SharedPreferencesProvider(context).putAuthorsData(popularAuthorsList, "popularauthors");

        progressDialog.dismiss();

    }







}
