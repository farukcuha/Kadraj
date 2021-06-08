package com.pandorina.kadraj.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.pandorina.kadraj.Fragments.HomepageFragment;
import com.pandorina.kadraj.Fragments.NewsFragment;
import com.pandorina.kadraj.Fragments.AuthorsFragment;
import com.pandorina.kadraj.R;
import com.pandorina.kadraj.Web.NewsWebView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        @RequiresApi(api = Build.VERSION_CODES.M)
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.haberler:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentcontainer, new NewsFragment())
                            .replace(R.id.newspagecontainer, new NewsWebView("https://www.sabah.com.tr", "Sabah"))
                            .setCustomAnimations(R.anim.enter, R.anim.exit).commit();
                    break;
                case R.id.anasayfa:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentcontainer, new HomepageFragment())
                            .setCustomAnimations(R.anim.enter, R.anim.exit).commit();

                    break;
                case R.id.yazarlar:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentcontainer, new AuthorsFragment())
                            .setCustomAnimations(R.anim.enter, R.anim.exit).commit();
                    break;
            }
            return true;
        }
    };
}