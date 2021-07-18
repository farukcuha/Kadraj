package com.pandorina.kadraj.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.pandorina.kadraj.Adapters.AuthorsAdapter;
import com.pandorina.kadraj.Adapters.PapernewsAdapter;
import com.pandorina.kadraj.Dialogs.ErrorDialog;
import com.pandorina.kadraj.SharedPreferencesProvider;
import com.pandorina.kadraj.Models.PapernewsModel;
import com.pandorina.kadraj.Tasks.PopularAuthorsTask;
import com.pandorina.kadraj.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AuthorsFragment extends Fragment {
    RecyclerView papernewsReyclerView, popularAuthorsRecyclerView;
    PapernewsAdapter papernewsAdapter;
    List<PapernewsModel> papernewsimages;
    AuthorsAdapter popularAuthorsAdapter;
    ErrorDialog errorDialog;
    SharedPreferences sharedPreferences;
    LinearLayout newspaperLayout;
    ShimmerFrameLayout skeleton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);

        newspaperLayout = view.findViewById(R.id.newspaperslayout);
        skeleton = view.findViewById(R.id.authorskeleton);

        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("kadrajcloud", Context.MODE_PRIVATE);

        sharedPreferences.edit().remove("newspaperauthors").apply();
        errorDialog = new ErrorDialog(getContext(), "İnternet Bağlantınızı Kontrol Ediniz.");

        papernewsReyclerView = view.findViewById(R.id.papernewsrecyclerview);
        popularAuthorsRecyclerView = view.findViewById(R.id.popularauthors);
        setUpPapernewsList();
        setUpAuthorList();

        return view;
    }

    private void setUpPapernewsList() {
        papernewsimages = new ArrayList<>();

        papernewsReyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        papernewsReyclerView.setHasFixedSize(true);

        papernewsimages.add(new PapernewsModel(R.drawable.sabahlogo, "https://www.sabah.com.tr/yazarlar", "Sabah"));
        papernewsimages.add(new PapernewsModel(R.drawable.sozcu, "https://www.sozcu.com.tr/kategori/yazarlar/", "Sözcü"));
        papernewsimages.add(new PapernewsModel(R.drawable.karar, "https://www.karar.com/yazarlar", "Karar"));
        papernewsimages.add(new PapernewsModel(R.drawable.turkiye, "https://www.turkiyegazetesi.com.tr/yazarlar", "Türkiye"));
        papernewsimages.add(new PapernewsModel(R.drawable.takvim, "https://www.takvim.com.tr/yazarlar", "Takvim"));
        papernewsimages.add(new PapernewsModel(R.drawable.haberturk, "https://www.haberturk.com/htyazarlar", "Habertürk"));
        papernewsimages.add(new PapernewsModel(R.drawable.hurriyet, "https://www.hurriyet.com.tr/yazarlar/", "Hürriyet"));
        papernewsimages.add(new PapernewsModel(R.drawable.milliyet, "https://www.milliyet.com.tr/yazarlar/", "Milliyet"));
        papernewsimages.add(new PapernewsModel(R.drawable.yenisafak, "https://www.yenisafak.com/yazarlar/bugun-yazanlar", "Yenişafak"));
        papernewsimages.add(new PapernewsModel(R.drawable.akit, "https://www.yeniakit.com.tr/yazarlar/", "Yeni Akit"));

        papernewsAdapter = new PapernewsAdapter(papernewsimages, getContext(), getFragmentManager());
        papernewsReyclerView.setAdapter(papernewsAdapter);

    }
    private void setUpAuthorList() {
        String resources = sharedPreferences.getString("popularauthors", "null");

        if (!resources.equals("null")){
            popularAuthorsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            popularAuthorsRecyclerView.setLayoutManager(linearLayoutManager);
            popularAuthorsAdapter = new AuthorsAdapter(new SharedPreferencesProvider(getContext()).getAuthorsData(resources, "popularauthors"), getContext(), getFragmentManager());
            popularAuthorsRecyclerView.setAdapter(popularAuthorsAdapter);
        }

        else {
            checkInternetConnection();
        }
    }

    private void checkInternetConnection(){
        ConnectivityManager connectivityManager = ((ConnectivityManager) Objects.requireNonNull(getActivity())
                .getSystemService(Context.CONNECTIVITY_SERVICE));

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            new PopularAuthorsTask(getContext(), popularAuthorsRecyclerView, getFragmentManager(), skeleton).execute();
            errorDialog.dismiss();
        }
        else {
            errorDialog.show();
        }
    }
}