package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.leagueapp.R;
import com.example.leagueapp.adapter.ChampionAdapter;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.presenter.ChampionPresenter;

import java.util.ArrayList;


public class ChampionsFragment extends Fragment implements ChampionContract.ChampionView, ChampionAdapter.OnChampClickListener {

    private static final String TAG = "ChampionsFragment";
    private ChampionContract.ChampionPresenter presenter = new ChampionPresenter();
    private ChampionAdapter championAdapter = new ChampionAdapter(this);

    public ChampionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Button detailsButton = view.findViewById(R.id.champDetails);
//        detailsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        RecyclerView championRecyclerView = view.findViewById(R.id.champion_recycler_view);
        championRecyclerView.setAdapter(championAdapter);
        presenter.onAttach(this);
        presenter.fetchChampions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_champions, container, false);
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
        Log.d(TAG, "onChampClick: " + championName);
        NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToDetailsFragment();
        Navigation.findNavController(getView()).navigate(action);
    }

    @Override
    public void addToFavorite(ChampionResponse.Champion champion) {
        Log.d(TAG, "addToFavorite: " + champion.toString());
    }
}