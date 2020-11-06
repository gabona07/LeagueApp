package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leagueapp.R;
import com.google.android.material.transition.MaterialFadeThrough;

public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialFadeThrough enterTransition = new MaterialFadeThrough();
        long duration = getResources().getInteger(R.integer.reply_motion_duration);
        enterTransition.setDuration(duration);
        setEnterTransition(enterTransition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}