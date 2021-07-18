package com.pandorina.kadraj.Fragments;

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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.pandorina.kadraj.Adapters.AuthorsAdapter;
import com.pandorina.kadraj.SharedPreferencesProvider;
import com.pandorina.kadraj.Tasks.AuthorsListTask;
import com.pandorina.kadraj.R;


public class AuthorsListFragment extends Fragment {
    String newspaperName;
    RecyclerView authorsRecyclerView;
    String newspaperUrl;
    TextView newspaperTextView;
    AuthorsAdapter popularAuthorsAdapter;
    ShimmerFrameLayout skeleton;

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
        skeleton = view.findViewById(R.id.authorskeleton);

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
            new AuthorsListTask(newspaperUrl, getContext(), getFragmentManager(), authorsRecyclerView, skeleton).execute();
        }

        return view;
    }
}