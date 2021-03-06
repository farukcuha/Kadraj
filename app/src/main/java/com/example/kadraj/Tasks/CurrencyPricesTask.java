package com.example.kadraj.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    private Context context;
    private View view;

    private TextView goldName1, goldName2, goldName3, currencyName1, currencyName2, cryptoName1, cryptoName2;
    private TextView goldPurchase1, goldPurchase2, goldPurchase3, currencyPurchase1, currencyPurchase2, cryptoPurchase1, cryptoPurchase2;
    private TextView goldSalePrice1, goldSalePrice2, goldSalePrice3, currencySalePrice1, currencySalePrice2, cryptoSalePrice1, cryptoSalePrice2;
    private TextView goldChanging1, goldChanging2, goldChanging3, currencyChanging1, currencyChanging2, cryptoChanging1, cryptoChanging2;
    private Document document;
    private List<CurrencyModel> goldList, currencyList, cryptoList;
    char[] changingControl = new char[2];

    public CurrencyPricesTask(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        idPairs(view);

        goldList = new ArrayList<>();
        currencyList = new ArrayList<>();
        cryptoList = new ArrayList<>();



        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        goldPrices();
        currencyPrices();
        cryptoPrices();


        return null;
    }


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

        goldPurchase1.setText(goldList.get(0).getPurchasePrice());
        goldPurchase2.setText(goldList.get(1).getPurchasePrice());
        goldPurchase3.setText(goldList.get(2).getPurchasePrice());
        currencyPurchase1.setText(currencyList.get(0).getPurchasePrice());
        currencyPurchase2.setText(currencyList.get(1).getPurchasePrice());

        goldSalePrice1.setText(goldList.get(0).getSalePrice());
        goldSalePrice2.setText(goldList.get(1).getSalePrice());
        goldSalePrice3.setText(goldList.get(2).getSalePrice());
        currencySalePrice1.setText(currencyList.get(0).getSalePrice());
        currencySalePrice1.setText(currencyList.get(1).getSalePrice());
        cryptoSalePrice1.setText(cryptoList.get(0).getSalePrice());
        cryptoSalePrice2.setText(cryptoList.get(1).getSalePrice());


        cryptoList.get(0).getChanging().getChars(0,2, changingControl,0)

        if (changingControl == "%-"){
            Log.d("a", "azalan");
            Log.d("asds", String.valueOf(changingControl));
        }
        else {
            Log.d("a", "artan");
            Log.d("asds", String.valueOf(changingControl));

        }



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
        currencySalePrice1 = view.findViewById(R.id.currencysaleprice2);
        cryptoSalePrice1 = view.findViewById(R.id.cryptosaleprice1);
        cryptoSalePrice2 = view.findViewById(R.id.cryptosaleprice2);

        goldChanging1 = view.findViewById(R.id.goldchanging1);
        goldChanging2 = view.findViewById(R.id.goldchanging2);
        goldChanging3 = view.findViewById(R.id.goldchanging3);
        currencyChanging1 = view.findViewById(R.id.currencychanging1);
        currencyChanging2 = view.findViewById(R.id.currencychanging2);
        cryptoChanging1 = view.findViewById(R.id.cryptochanging1);
        cryptoChanging1 = view.findViewById(R.id.cryptochanging2);







    }

    private void goldPrices(){
        try {
            document = Jsoup.connect("https://altin.doviz.com").ignoreContentType(true).get();
            Elements elements = document.select("table[id=golds]");
            for (int i = 2; i < 5; i++){
                String str_goldName = elements.select("tr").get(i).select("td").get(0).text();
                char[] newGoldName = new char[str_goldName.length()-6];
                str_goldName.getChars(0,str_goldName.length()-6, newGoldName,0);

                /*String str_goldChanging = elements.select("tr").get(i).select("td").get(3).text();
                char[] checkMinusPlus = new char[2];
                str_goldChanging.getChars(0,1,checkMinusPlus, 0);

                if (String.valueOf(checkMinusPlus).equals("%-")){

                }*/

                Log.d("ad", String.valueOf(newGoldName));
                Log.d("alış", elements.select("tr").get(i).select("td").get(1).text());
                Log.d("satış", elements.select("tr").get(i).select("td").get(2).text());
                Log.d("değişim", elements.select("tr").get(i).select("td").get(3).text());

                goldList.add(new CurrencyModel(
                        String.valueOf(newGoldName),
                        elements.select("tr").get(i).select("td").get(1).text(),
                        elements.select("tr").get(i).select("td").get(2).text(),
                        elements.select("tr").get(i).select("td").get(3).text()

                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void currencyPrices() {
        try {
            document = Jsoup.connect("https://kur.doviz.com").ignoreContentType(true).get();
            Elements elements = document.select("table[id=currencies] > tbody");
            for (int i = 0; i < 2; i++){
                String str_CurrencyName = elements.select("tr").get(i).select("td").get(0).text();
                char[] newCurrencyName = new char[3];
                str_CurrencyName.getChars(0,3, newCurrencyName,0);

                Log.d("ad", String.valueOf(newCurrencyName));
                Log.d("alış", elements.select("tr").get(i).select("td").get(1).text());
                Log.d("satış", elements.select("tr").get(i).select("td").get(2).text());
                Log.d("değişim", elements.select("tr").get(i).select("td").get(5).text());

                currencyList.add(new CurrencyModel(
                        String.valueOf(newCurrencyName),
                        elements.select("tr").get(i).select("td").get(1).text(),
                        elements.select("tr").get(i).select("td").get(2).text(),
                        elements.select("tr").get(i).select("td").get(5).text()

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



                Log.d("ad", String.valueOf(newCryptoName));
                Log.d("alış", "null");
                Log.d("satış", elements.select("tr").get(i).select("td").get(2).text());
                Log.d("değişim", elements.select("tr").get(i).select("td").get(5).text());

                cryptoList.add(new CurrencyModel(
                        String.valueOf(newCryptoName),
                        "null",
                        elements.select("tr").get(i).select("td").get(2).text(),
                        elements.select("tr").get(i).select("td").get(5).text()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
