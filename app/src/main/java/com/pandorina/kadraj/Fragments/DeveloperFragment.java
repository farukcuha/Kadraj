package com.pandorina.kadraj.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pandorina.kadraj.R;

import java.util.Objects;


public class DeveloperFragment extends Fragment implements View.OnClickListener {
    TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_developer, container, false);
        textView = view.findViewById(R.id.info);

        String text =
                "<b>Yerel Haberler:<b> <u>https://www.hurriyet.com.tr</u><br>" +
                "<b>Hava Durumu:<b> <u>https://www.mgm.gov.tr</u><br>" +
                "<b>Güncel Döviz Kurları:<b> <u>https://www.doviz.com</u><br>" +
                "<b>Gazete Yazarları:<b> <u>https://www.sabah.com.tr</u><br>" +
                        "<u>https://www.sozcu.com.tr</u><br>" +
                        "<u>https://www.haberturk.com</u><br>" +
                        "<u>https://www.hurriyet.com.tr</u><br>" +
                        "<u>https://www.karar.com/</u><br>" +
                        "<u>https://www.milliyet.com.tr</u><br>" +
                        "<u>https://www.turkiyegazetesi.com.tr</u><br>" +
                        "<u>https://www.takvim.com.tr</u><br>" +
                        "<u>https://www.yeniakit.com.tr</u><br>" +
                        "<u>https://www.yenisafak.com</u><br><br>" +
                "Uygulamaya dair görüşlerinizi, öneri ve yorumlarınızı aşağıdaki mail simgesine tıklayarak tarafımıza ulaştırabilirsiniz.<br><br>" +
                "<b>Geliştirici</b><br>" +
                "Ahmet Faruk Çuha";

        textView.setText(Html.fromHtml(text));

        view.findViewById(R.id.github).setOnClickListener(this);
        view.findViewById(R.id.twitter).setOnClickListener(this);
        view.findViewById(R.id.linkedin).setOnClickListener(this);

        view.findViewById(R.id.gmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:ahmetfarukcuha@gmail.com"));
                if (intent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String url = null;
        switch (v.getId()){
            case R.id.github:
                url = "https://github.com/farukcuha";
                break;
            case R.id.linkedin:
                url = "https://www.linkedin.com/in/ahmet-faruk-%C3%A7uha-5a8209116/";
                break;
            case R.id.twitter:
                url = "https://twitter.com/faruk__cuha";
                break;
            default:
                break;
        }
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}