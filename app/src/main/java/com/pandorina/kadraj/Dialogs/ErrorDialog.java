package com.pandorina.kadraj.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pandorina.kadraj.R;

public class ErrorDialog {
    Context context;
    String text;
    Dialog dialog;
    View dialogView;
    TextView textView;

    public ErrorDialog(Context context, String text) {
        this.context = context;
        this.text = text;

        dialog = new Dialog(context);
        dialogView = LayoutInflater.from(context).inflate(R.layout.errordialogitem, null);
        textView = dialogView.findViewById(R.id.errortext);
        textView.setText(text);
        dialog.setContentView(dialogView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
    }

    public void show(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }



}
