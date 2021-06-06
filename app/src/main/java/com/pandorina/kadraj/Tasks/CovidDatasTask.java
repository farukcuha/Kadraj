package com.pandorina.kadraj.Tasks;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pandorina.kadraj.Dialogs.CustomProgressDialog;
import com.pandorina.kadraj.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitHelper;

public class CovidDatasTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    @SuppressLint("StaticFieldLeak")
    View view;
    Dialog dialog;
    @SuppressLint("StaticFieldLeak")
    TextView todayTestNumber, todayCaseNumber, todayPatientNumber, todayRipNumber, todayHealingNumber;
    @SuppressLint("StaticFieldLeak")
    TextView totalTestNumber, totalCaseNumber, totalRipNumber, heavyPatientNumber, totalHealingNumber;
    @SuppressLint("StaticFieldLeak")
    TextView totalSyringe, lastUpdate;
    List<String> covidList;

    public CovidDatasTask(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new CustomProgressDialog(context).loadingDialog();
        dialog.show();

        covidList = new ArrayList<>();

        todayTestNumber = view.findViewById(R.id.todaytestnumber);
        todayCaseNumber = view.findViewById(R.id.todaycasenumber);
        todayPatientNumber = view.findViewById(R.id.todaypatientnumber);
        todayRipNumber = view.findViewById(R.id.todayripnumber);
        todayHealingNumber = view.findViewById(R.id.todayhealingnumber);
        totalTestNumber = view.findViewById(R.id.totaltestnumber);
        totalCaseNumber = view.findViewById(R.id.totalcasenumber);
        totalRipNumber = view.findViewById(R.id.totaldiednumber);
        heavyPatientNumber = view.findViewById(R.id.heavypatientnumber);
        totalHealingNumber = view.findViewById(R.id.totalhealingnumber);
        totalSyringe = view.findViewById(R.id.totalsyringe);
        lastUpdate = view.findViewById(R.id.covidlastupdatedate);

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("https://www.yenisafak.com/").ignoreContentType(true).get();

            for (Element element : document.select("div.entry-block > div.entry")){
                Log.d("a", element.select("div.value").text());
                covidList.add(element.select("div.value").text());
            }

            covidList.add(document.select("div[class=syringe-block]").select("div[class=syringe-item]").first().select("span").text());
            covidList.add(document.select("div.covid-title > div.text > span").text());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        todayTestNumber.setText(covidList.get(0));
        AutofitHelper.create(todayTestNumber);
        todayCaseNumber.setText(covidList.get(1));
        todayPatientNumber.setText(covidList.get(2));
        todayRipNumber.setText(covidList.get(3));
        todayHealingNumber.setText(covidList.get(4));
        totalTestNumber.setText(covidList.get(5));
        AutofitHelper.create(totalTestNumber);
        totalCaseNumber.setText(covidList.get(6));
        AutofitHelper.create(totalCaseNumber);
        totalRipNumber.setText(covidList.get(7));
        heavyPatientNumber.setText(covidList.get(8));
        totalHealingNumber.setText(covidList.get(9));
        AutofitHelper.create(totalHealingNumber);
        totalSyringe.setText(covidList.get(10));
        AutofitHelper.create(totalSyringe);
        lastUpdate.setText(covidList.get(11));

        dialog.dismiss();
    }
}
