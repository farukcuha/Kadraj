package com.example.kadraj.Dialogs;

import android.app.Dialog;
import android.content.Context;

import com.example.kadraj.R;

public class CustomProgressDialog{
    private final Context context;

    public CustomProgressDialog(Context context) {
        this.context = context;
    }

    public Dialog loadingDialog(){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.loadingdialogitem);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);

        return dialog;
    }
}
