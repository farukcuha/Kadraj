package com.example.kadraj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.example.kadraj.Fragments.HomepageFragment;
import com.example.kadraj.Fragments.NewsFragment;
import com.example.kadraj.Fragments.AuthorsFragment;
import com.example.kadraj.Web.NewsWebView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, new HomepageFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.mainbottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        bottomNavigationView.setItemIconTintList(null);

    }
    private final BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.haberler:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentcontainer, new NewsFragment())
                            .replace(R.id.newspagecontainer, new NewsWebView("https://www.sabah.com.tr", "Sabah")).setCustomAnimations(R.anim.enter, R.anim.exit).commit();
                    break;
                case R.id.anasayfa:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentcontainer, new HomepageFragment()).setCustomAnimations(R.anim.enter, R.anim.exit).commit();
                    break;
                case R.id.yazarlar:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentcontainer, new AuthorsFragment()).setCustomAnimations(R.anim.enter, R.anim.exit).commit();
                    break;
            }
            return true;
        }
    };


}