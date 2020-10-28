package com.example.leagueapp.contract;

import com.example.leagueapp.model.ChampionResponse;

import java.util.ArrayList;

public interface ChampionContract {

    interface ChampionView extends BaseContract.BaseView {
        void displayChampions(ArrayList<ChampionResponse.Champion> champions);
    }

    interface ChampionPresenter extends BaseContract.BasePresenter {
        void fetchChampions();
    }
}
