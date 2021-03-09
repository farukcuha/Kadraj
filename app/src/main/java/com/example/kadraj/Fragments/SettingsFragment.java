package com.example.kadraj.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kadraj.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class SettingsFragment extends Fragment {
    private ImageButton button;
    private ChipGroup chipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_settings, container, false);

        button = view.findViewById(R.id.newsaddingbutton);
        chipGroup = view.findViewById(R.id.newschipgroup);





        return view;
    }
}
