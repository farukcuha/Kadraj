package com.example.kadraj.Tasks;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.kadraj.CustomProgressDialog;
import com.example.kadraj.Models.CovidModels;
import com.example.kadraj.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CovidTask extends AsyncTask<Void, Void, Void> {
    View view;
    Context context;
    private TextView todayTestNumber, todayCaseNumber, todayPatientNumber, todayDeathNumber, todayHealingNumber;
    private TextView allTestNumber, allCaseNumber, allHeavyPatientNumber, allDeathNumber, allHealingNumber;
    private List<CovidModels> list;
    private Dialog progressDialog;
    private WebView webView;

    public CovidTask(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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

        list = new ArrayList<>();
        progressDialog = new CustomProgressDialog(context).loadingDialog();
        progressDialog.show();

        webView = new WebView(context);

        Log.d("a", "task is starting");

    }

    @Override
    protected Void doInBackground(Void... voids) {




        try {
            Document document = Jsoup.connect("https://covid19.saglik.gov.tr").ignoreContentType(true).userAgent("USER_AGENT_HERE").get();


            /*list.add(new CovidModels(
                    document.select("h3[class=full_date]").text(),
                    document.select("p[class=large bugunku-test-sayisi]").text(),
                    document.select("p[class=large bugunku-vaka-sayisi]").text(),
                    document.select("p[class=large bugunku-hasta-sayisi]").text(),
                    document.select("p[class=large bugunku-vefat-sayisi]").text(),
                    document.select("p[class=large bugunku-vefat-sayisi]").text(),
                    document.select("p[class=large bugunku-vefat-sayisi]").text(),
                    document.select("p[class=large bugunku-vefat-sayisi]").text(),
                    document.select("p[class=large bugunku-vefat-sayisi]").text(),
                    document.select("p[class=large bugunku-vefat-sayisi]").text(),
                    document.select("p[class=large bugunku-vefat-sayisi]").text()

            ));*/
            //Log.d("a", document.select("h5[class=large]"));



        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}
