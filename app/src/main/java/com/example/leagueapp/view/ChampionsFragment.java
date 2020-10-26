package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

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


public class ChampionsFragment extends Fragment implements ChampionContract.ChampionView {

    private ChampionContract.ChampionPresenter presenter = new ChampionPresenter();
    private ChampionAdapter championAdapter = new ChampionAdapter();

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
//                NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToDetailsFragment();
//                Navigation.findNavController(view).navigate(action);
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
}