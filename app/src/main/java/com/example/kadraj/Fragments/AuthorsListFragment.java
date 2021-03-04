package com.example.kadraj.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kadraj.Adapters.AuthorsAdapter;
import com.example.kadraj.SharedPreferencesProvider;
import com.example.kadraj.Tasks.AuthorsListTask;
import com.example.kadraj.R;


public class AuthorsListFragment extends Fragment {
    private String newspaperName;
    private RecyclerView authorsRecyclerView;
    private String newspaperUrl;
    private TextView newspaperTextView;
    private AuthorsAdapter popularAuthorsAdapter;

    public AuthorsListFragment(String  newspaperName, String newspaperUrl) {
        this.newspaperName = newspaperName;
        this.newspaperUrl = newspaperUrl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors_list, container, false);

        newspaperTextView = view.findViewById(R.id.newspapername);
        authorsRecyclerView = view.findViewById(R.id.authorslistreyclerview);

        newspaperTextView.setText(newspaperName);

        String resources = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("newspaperauthors", "null");

        if (!resources.equals("null")){
            authorsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            authorsRecyclerView.setLayoutManager(linearLayoutManager);
            popularAuthorsAdapter = new AuthorsAdapter(new SharedPreferencesProvider(
                    getContext()).getAuthorsData(resources, "newspaperauthors"),
                    getContext(),
                    getFragmentManager());
            authorsRecyclerView.setAdapter(popularAuthorsAdapter);
            Log.d("sadsfsgfdhjh", "alhaalıdo");

        }


        else {
            new AuthorsListTask(newspaperUrl, getContext(), getFragmentManager(), authorsRecyclerView).execute();
        }


        return view;

    }

}