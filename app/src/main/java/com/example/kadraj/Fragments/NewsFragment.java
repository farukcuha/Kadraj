package com.example.kadraj.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.kadraj.Web.NewsWebView;
import com.example.kadraj.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class NewsFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        bottomNavigationView = view.findViewById(R.id.news_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        bottomNavigationView.setItemIconTintList(null);

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.newssabah:
                    fragment = new NewsWebView("https://www.sabah.com.tr", "Sabah");
                    break;
                case R.id.newssozcu:
                    fragment = new NewsWebView("https://www.sozcu.com.tr/kategori/gundem/", "Sözcü");
                    break;
                case R.id.newsbozyazi:
                    fragment = new NewsWebView("https://www.bozyazihaber.com/index.php", "Bozyazı Haber");
                    break;
            }
            getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.newspagecontainer, fragment).commit();
            return true;
        }
    };
}