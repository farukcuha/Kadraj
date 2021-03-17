package com.example.kadraj.Tasks;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kadraj.CustomProgressDialog;
import com.example.kadraj.Models.CurrencyModel;
import com.example.kadraj.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CurrencyPricesTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    @SuppressLint("StaticFieldLeak")
    View view;
    @SuppressLint("StaticFieldLeak")
    private TextView goldName1, goldName2, goldName3, currencyName1, currencyName2, cryptoName1, cryptoName2;
    @SuppressLint("StaticFieldLeak")
    private TextView goldPurchase1, goldPurchase2, goldPurchase3, currencyPurchase1, currencyPurchase2;
    @SuppressLint("StaticFieldLeak")
    private TextView goldSalePrice1, goldSalePrice2, goldSalePrice3, currencySalePrice1, currencySalePrice2, cryptoSalePrice1, cryptoSalePrice2;
    @SuppressLint("StaticFieldLeak")
    private TextView goldChanging1, goldChanging2, goldChanging3, currencyChanging1, currencyChanging2, cryptoChanging1, cryptoChanging2;
    private Document document;
    private List<CurrencyModel> goldList, currencyList, cryptoList;
    private final Dialog progressDialog;

    public CurrencyPricesTask(Context context, View view) {
        this.context = context;
        this.view = view;
        this.progressDialog = new CustomProgressDialog(context).loadingDialog();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        idPairs(view);
        goldList = new ArrayList<>();
        currencyList = new ArrayList<>();
        cryptoList = new ArrayList<>();

        if (progressDialog!=null){
            progressDialog.show();
        }
        else {
            progressDialog.dismiss();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        goldPrices();
        currencyPrices();
        cryptoPrices();


        return null;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        goldName1.setText(goldList.get(0).getName());
        goldName2.setText(goldList.get(1).getName());
        goldName3.setText(goldList.get(2).getName());
        currencyName1.setText(currencyList.get(0).getName());
        currencyName2.setText(currencyList.get(1).getName());
        cryptoName1.setText(cryptoList.get(0).getName());
        cryptoName2.setText(cryptoList.get(1).getName());

        goldPurchase1.setText("₺" + goldList.get(0).getPurchasePrice());
        goldPurchase2.setText("₺" + goldList.get(1).getPurchasePrice());
        goldPurchase3.setText("₺" + goldList.get(2).getPurchasePrice());
        currencyPurchase1.setText("₺" + currencyList.get(0).getPurchasePrice());
        currencyPurchase2.setText("₺" + currencyList.get(1).getPurchasePrice());

        goldSalePrice1.setText("₺" + goldList.get(0).getSalePrice());
        goldSalePrice2.setText("₺" + goldList.get(1).getSalePrice());
        goldSalePrice3.setText("₺" + goldList.get(2).getSalePrice());
        currencySalePrice1.setText("₺" + currencyList.get(0).getSalePrice());
        currencySalePrice2.setText("₺" + currencyList.get(1).getSalePrice());
        cryptoSalePrice1.setText(cryptoList.get(0).getSalePrice());
        cryptoSalePrice2.setText(cryptoList.get(1).getSalePrice());

        goldChanging1.setText(goldList.get(0).getChanging());
        goldChanging2.setText(goldList.get(1).getChanging());
        goldChanging3.setText(goldList.get(2).getChanging());
        currencyChanging1.setText(currencyList.get(0).getChanging());
        currencyChanging2.setText(currencyList.get(1).getChanging());
        cryptoChanging1.setText(cryptoList.get(0).getChanging());
        cryptoChanging2.setText(cryptoList.get(1).getChanging());

        if (goldList.get(0).getRotation().equals("down")){
            goldChanging1.setTextColor(Color.RED);
        }
        if (goldList.get(1).getRotation().equals("down")){
            goldChanging2.setTextColor(Color.RED);
        }
        if (goldList.get(2).getRotation().equals("down")){
            goldChanging3.setTextColor(Color.RED);
        }
        if (currencyList.get(0).getRotation().equals("down")){
            currencyChanging1.setTextColor(Color.RED);
        }
        if (currencyList.get(1).getRotation().equals("down")){
            currencyChanging2.setTextColor(Color.RED);
        }
        if (cryptoList.get(0).getRotation().equals("down")){
            cryptoChanging1.setTextColor(Color.RED);

        }
        if (cryptoList.get(1).getRotation().equals("down")){
            cryptoChanging2.setTextColor(Color.RED);
        }
        progressDialog.dismiss();

    }

    private void idPairs(View view){
        goldName1 = view.findViewById(R.id.goldname1);
        goldName2 = view.findViewById(R.id.goldname2);
        goldName3 = view.findViewById(R.id.goldname3);
        currencyName1 = view.findViewById(R.id.currencyname1);
        currencyName2 = view.findViewById(R.id.currencyname2);
        cryptoName1 = view.findViewById(R.id.cryptoname1);
        cryptoName2 = view.findViewById(R.id.cryptoname2);

        goldPurchase1 = view.findViewById(R.id.goldpurchaseprice1);
        goldPurchase2 = view.findViewById(R.id.goldpurchaseprice2);
        goldPurchase3 = view.findViewById(R.id.goldpurchaseprice3);
        currencyPurchase1 = view.findViewById(R.id.currencypurchaseprice1);
        currencyPurchase2 = view.findViewById(R.id.currencypurchaseprice2);

        goldSalePrice1 = view.findViewById(R.id.goldsaleprice1);
        goldSalePrice2 = view.findViewById(R.id.goldsaleprice2);
        goldSalePrice3 = view.findViewById(R.id.goldsaleprice3);
        currencySalePrice1 = view.findViewById(R.id.currencysaleprice1);
        currencySalePrice2 = view.findViewById(R.id.currencysaleprice2);
        cryptoSalePrice1 = view.findViewById(R.id.cryptosaleprice1);
        cryptoSalePrice2 = view.findViewById(R.id.cryptosaleprice2);

        goldChanging1 = view.findViewById(R.id.goldchanging1);
        goldChanging2 = view.findViewById(R.id.goldchanging2);
        goldChanging3 = view.findViewById(R.id.goldchanging3);
        currencyChanging1 = view.findViewById(R.id.currencychanging1);
        currencyChanging2 = view.findViewById(R.id.currencychanging2);
        cryptoChanging1 = view.findViewById(R.id.cryptochanging1);
        cryptoChanging2 = view.findViewById(R.id.cryptochanging2);


    }

    private void goldPrices(){
        try {
            document = Jsoup.connect("https://altin.doviz.com").ignoreContentType(true).get();
            Elements elements = document.select("table[id=golds]");
            for (int i = 2; i < 5; i++){
                String str_goldName = elements.select("tr").get(i).select("td").get(0).text();
                char[] newGoldName = new char[str_goldName.length()-6];
                str_goldName.getChars(0,str_goldName.length()-6, newGoldName,0);

                String str_goldChanging = elements.select("tr").get(i).select("td").get(3).text();
                char[] checkMinusPlus = new char[2];
                str_goldChanging.getChars(0,2,checkMinusPlus, 0);

                String rotation;


                if (String.valueOf(checkMinusPlus).equals("%-")){
                    rotation = "down";

                    Log.d("a", String.valueOf(checkMinusPlus));
                }
                else {
                    rotation = "up";
                    Log.d("a", String.valueOf(checkMinusPlus));

                }

                Log.d("ad", String.valueOf(newGoldName));
                Log.d("alış", elements.select("tr").get(i).select("td").get(1).text());
                Log.d("satış", elements.select("tr").get(i).select("td").get(2).text());
                Log.d("değişim", elements.select("tr").get(i).select("td").get(3).text());


                goldList.add(new CurrencyModel(
                        String.valueOf(newGoldName),
                        elements.select("tr").get(i).select("td").get(1).text(),
                        elements.select("tr").get(i).select("td").get(2).text(),
                        elements.select("tr").get(i).select("td").get(3).text(),
                        rotation

                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    //private void getDatas(String url, String documentPath, int startLoop, int finishLoop, )
    private void currencyPrices() {
        try {
            document = Jsoup.connect("https://kur.doviz.com").ignoreContentType(true).get();
            Elements elements = document.select("table[id=currencies] > tbody");
            for (int i = 0; i < 2; i++){
                String str_CurrencyName = elements.select("tr").get(i).select("td").get(0).text();
                char[] newCurrencyName = new char[3];
                str_CurrencyName.getChars(0,3, newCurrencyName,0);

                String str_CurrencyChanging = elements.select("tr").get(i).select("td").get(5).text();
                char[] checkMinusPlus = new char[2];
                str_CurrencyChanging.getChars(0,2,checkMinusPlus, 0);

                String rotation;

                if (String.valueOf(checkMinusPlus).equals("%-")){
                    rotation = "down";
                }
                else {
                    rotation = "up";
                }

                Log.d("ad", String.valueOf(newCurrencyName));
                Log.d("alış", elements.select("tr").get(i).select("td").get(1).text());
                Log.d("satış", elements.select("tr").get(i).select("td").get(2).text());
                Log.d("değişim", elements.select("tr").get(i).select("td").get(5).text());

                currencyList.add(new CurrencyModel(
                        String.valueOf(newCurrencyName),
                        elements.select("tr").get(i).select("td").get(1).text(),
                        elements.select("tr").get(i).select("td").get(2).text(),
                        elements.select("tr").get(i).select("td").get(5).text(),
                        rotation
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cryptoPrices() {
        try {
            document = Jsoup.connect("https://www.doviz.com/kripto-paralar").ignoreContentType(true).get();
            Elements elements = document.select("table[id=coins] > tbody");
            for (int i = 0; i < 2; i++){

                String str_CryptoName = elements.select("tr").get(i).select("td").get(0).select("a").text();
                char[] newCryptoName = new char[3];
                str_CryptoName.getChars(0,3, newCryptoName,0);

                String str_CryptoChanging = elements.select("tr").get(i).select("td").get(5).text();
                char[] checkMinusPlus = new char[2];
                str_CryptoChanging.getChars(0,2,checkMinusPlus, 0);

                String rotation;

                if (String.valueOf(checkMinusPlus).equals("%-")){
                    rotation = "down";
                }
                else {
                    rotation = "up";
                }
                Log.d("ad", String.valueOf(newCryptoName));
                Log.d("alış", "null");
                Log.d("satış", elements.select("tr").get(i).select("td").get(2).text());
                Log.d("değişim", elements.select("tr").get(i).select("td").get(5).text());

                cryptoList.add(new CurrencyModel(
                        String.valueOf(newCryptoName),
                        "null",
                        elements.select("tr").get(i).select("td").get(2).text(),
                        elements.select("tr").get(i).select("td").get(5).text(),
                        rotation
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
