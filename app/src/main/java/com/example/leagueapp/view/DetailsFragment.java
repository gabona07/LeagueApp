package com.example.leagueapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.leagueapp.R;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.DetailsResponse;
import com.example.leagueapp.presenter.DetailsPresenter;
import com.google.android.material.appbar.MaterialToolbar;


public class DetailsFragment extends Fragment implements ChampionContract.DetailsView {

    private static final String TAG = "DetailsFragment";
    private ProgressBar loadingBar;
    private ImageView profileImage;
    private ChampionContract.DetailsPresenter presenter = new DetailsPresenter();

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarInit(view);
        if (getArguments() != null) {
            profileImage = view.findViewById(R.id.profileImage);
            //loadingBar = view.findViewById(R.id.championsLoading);
            String championName = DetailsFragmentArgs.fromBundle(getArguments()).getChampionName();
            presenter.fetchChampionDetails(championName);
        }
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    private void toolbarInit(View view) {
        final NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        MaterialToolbar toolbar = view.findViewById(R.id.detailsAppBar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavDirections action = DetailsFragmentDirections.actionDetailsFragmentToFavoriteFragment();
                navController.navigate(action);
                return true;
            }
        });
    }

    @Override
    public void displayChampionDetails(DetailsResponse.Detail champion) {
        Log.d(TAG, "displayChampionDetails: " + champion.getImage().getFull());
        Glide.with(profileImage).load(champion.getImage().getIconUrl()).into(profileImage);
    }

    @Override
    public void showLoading() {
        //loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        //loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception exception) {

    }
}