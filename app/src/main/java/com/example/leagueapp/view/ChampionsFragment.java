package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.leagueapp.R;
import com.example.leagueapp.adapter.ChampionAdapter;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.presenter.ChampionPresenter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;


public class ChampionsFragment extends Fragment implements ChampionContract.ChampionView, ChampionAdapter.OnChampClickListener {

    private static final String TAG = "ChampionsFragment";
    private ProgressBar loadingBar;
    private ChampionContract.ChampionPresenter presenter = new ChampionPresenter();
    private ChampionAdapter championAdapter = new ChampionAdapter(this);

    public ChampionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onAttach(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_champions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingBar = view.findViewById(R.id.championsLoading);
        RecyclerView championRecyclerView = view.findViewById(R.id.championsRecyclerView);
        championRecyclerView.setAdapter(championAdapter);
        toolBarInit(view);
        // Navigation Component always rebuilds the fragment's view,
        // so this is a workaround to prevent fetching the champions again (we could also use LiveData)
        if (!championAdapter.hasChampions()) presenter.fetchChampions();
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception exception) {

    }

    @Override
    public void displayChampions(ArrayList<ChampionResponse.Champion> champions) {
        championAdapter.setChampionList(champions);
    }

    @Override
    public void onChampClick(String championName) {
        NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToDetailsFragment(championName);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void addToFavorite(ChampionResponse.Champion champion) {
        Log.d(TAG, "addToFavorite: " + champion.toString());
    }

    private void toolBarInit(View view) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        MaterialToolbar toolbar = view.findViewById(R.id.championsAppBar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }
}