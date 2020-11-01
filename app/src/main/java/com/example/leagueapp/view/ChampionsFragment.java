package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.leagueapp.widget.ChampionSearchView;
import com.example.leagueapp.R;
import com.example.leagueapp.adapter.ChampionAdapter;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.presenter.ChampionPresenter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;


public class ChampionsFragment extends Fragment implements ChampionContract.ChampionView, ChampionAdapter.OnChampClickListener {

    private static final String LOG_TAG = "ChampionsFragment";
    private ChampionContract.ChampionPresenter championPresenter = new ChampionPresenter();
    private MaterialToolbar toolbar;
    private ProgressBar loadingBar;
    private ChampionAdapter championAdapter;

    public ChampionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        championAdapter = new ChampionAdapter(this);
        championPresenter.onAttach(this);
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
        toolbarInit(view);
        // Navigation Component always rebuilds the fragment's view,
        // so this is a workaround to prevent fetching the champions again (we could also use LiveData)
        if (!championAdapter.holdsChampions()) {
            championPresenter.fetchChampions();
        }
    }

    @Override
    public void onDestroy() {
        championAdapter.onDestroy();
        championPresenter.onDetach();
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
        toolbar.collapseActionView();
        NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToDetailsFragment(championName);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void addToFavorite(ChampionResponse.Champion champion) {
        Log.d(LOG_TAG, "addToFavorite: " + champion.toString());
    }

    private void toolbarInit(View view) {
        final NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        toolbar = view.findViewById(R.id.championsAppBar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        searchViewInit();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.favorite) {
                    toolbar.collapseActionView();
                    NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToFavoriteFragment();
                    navController.navigate(action);
                    return true;
                }
                return false;
            }
        });
    }

    private void searchViewInit() {
        final ChampionSearchView searchView = (ChampionSearchView) toolbar.getMenu().findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                championAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}