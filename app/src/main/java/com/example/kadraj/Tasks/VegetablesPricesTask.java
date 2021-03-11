package com.example.kadraj.Tasks;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kadraj.CustomProgressDialog;
import com.example.kadraj.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VegetablesPricesTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private View view;
    private TextView cucumberPrice, eggplantPrice, beanPrice, pepperPrice1, pepperPrice2, tomatoPrice, lastUpdate;
    private Dialog dialog;
    private List<String> pricesList;
    private String str_lastUpdate;

    public VegetablesPricesTask(Context context, View view) {
        this.context = context;
        this.view = view;
        this.dialog = new CustomProgressDialog(context).loadingDialog();
    }

    @Override
    protected void onPreExecute() {
        if (dialog!=null){
            dialog.show();
        }
        else{
            dialog.dismiss();
        }
        lastUpdate = view.findViewById(R.id.lastupdate);
        cucumberPrice = view.findViewById(R.id.cucumberprice);
        eggplantPrice = view.findViewById(R.id.eggplantprice);
        beanPrice = view.findViewById(R.id.beanprice);
        pepperPrice1 = view.findViewById(R.id.pepperprice1);
        pepperPrice2 = view.findViewById(R.id.pepperprice2);
        tomatoPrice = view.findViewById(R.id.tomatoprice);
        pricesList = new ArrayList<>();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document document = Jsoup.connect("http://www.tekeliajans.net").ignoreContentType(true).get();
            for (int i = 1; i < 7; i++){
                Log.d("a", document.select("font[color=#009900]").get(i).text());
                pricesList.add(document.select("font[color=#009900]").get(i).text());

            }
            str_lastUpdate = document.select("div[class=stil32]").text();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        lastUpdate.setText(str_lastUpdate);
        cucumberPrice.setText(pricesList.get(0));
        eggplantPrice.setText(pricesList.get(1));
        beanPrice.setText(pricesList.get(2));
        pepperPrice1.setText(pricesList.get(3));
        pepperPrice2.setText(pricesList.get(4));
        tomatoPrice.setText(pricesList.get(5));
        dialog.dismiss();
    }
}
