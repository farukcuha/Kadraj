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


public class DeveloperFragment extends Fragment implements View.OnClickListener {
    TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_developer, container, false);
        textView = view.findViewById(R.id.info);

        String text = "<b>Kaynaklar</b><br>" +
                "Yerel haberler Hürriyet gazetesinin web sayfasından alınmıştır.<br>" +
                "Hava durumu bilgileri MGM'den alınmıştır.<br>" +
                "Güncel covid-19 verileri Yenişafak gazetesinin web sayfasından alınmıştır.<br>" +
                "Güncel döviz verileri döviz.com'dan alınmıştır.<br>" +
                "Tüm gazete yazarlarının verileri gazetelerin kendi web sayfalarından alınmıştır.<br><br>" +
                "Uygulamaya dair görüşlerinizi lütfen yorumlarda belirtin.<br><br>" +
                "<b>Geliştirici</b><br>" +
                "Ahmet Faruk Çuha";
        textView.setText(Html.fromHtml(text));

        view.findViewById(R.id.github).setOnClickListener(this);
        view.findViewById(R.id.twitter).setOnClickListener(this);
        view.findViewById(R.id.linkedin).setOnClickListener(this);

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