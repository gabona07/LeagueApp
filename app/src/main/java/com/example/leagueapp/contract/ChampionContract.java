package com.example.leagueapp.contract;

import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DetailsResponse;

import java.util.ArrayList;

public interface ChampionContract {

    interface ChampionView extends BaseContract.BaseView {
        void displayChampions(ArrayList<ChampionResponse.Champion> champions);
    }

    interface ChampionPresenter extends BaseContract.BasePresenter {
        void fetchChampions();
    }

    interface DetailsView extends BaseContract.BaseView {
        void displayChampionDetails(DetailsResponse.Detail champion);
    }

    interface DetailsPresenter extends BaseContract.BasePresenter {
        void fetchChampionDetails(String championName);
    }
}
