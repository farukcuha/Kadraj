package com.pandorina.kadraj.Dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentManager;

import com.pandorina.kadraj.Fragments.SettingsFragment;
import com.pandorina.kadraj.R;

public class GoingToSettingsDialog implements View.OnClickListener {
    Context context;
    FragmentManager fragmentManager;

    Dialog dialog;
    View view;


    @SuppressLint("InflateParams")
    public GoingToSettingsDialog(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;

        dialog = new Dialog(context);
        view = LayoutInflater.from(context).inflate(R.layout.goingtosettingsdialogitem, null);

        ImageButton cancel = view.findViewById(R.id.close);
        Button settings = view.findViewById(R.id.goingtosettings);

        cancel.setOnClickListener(this);
        settings.setOnClickListener(this);

        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void show(){
        dialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                dialog.dismiss();
                break;
            case R.id.goingtosettings:
                dialog.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentcontainer, new SettingsFragment())
                        .addToBackStack("TAG").commit();

                break;
        }
    }
}
