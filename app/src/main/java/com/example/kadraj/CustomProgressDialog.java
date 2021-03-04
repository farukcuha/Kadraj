package com.example.kadraj;

import android.app.Dialog;
import android.content.Context;

public class CustomProgressDialog{
    private final Context context;

    public CustomProgressDialog(Context context) {
        this.context = context;
    }

    public Dialog loadingDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.loadingdialogitem);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }
}
