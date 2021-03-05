package com.example.kadraj.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;


public class CurrencyPricesTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private View view;

    public CurrencyPricesTask(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {



        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
