package com.example.leagueapp.contract;

import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DetailsResponse;

import java.util.List;

public interface ChampionContract {

    interface ChampionView extends BaseContract.BaseView {
        void displayChampions(List<ChampionResponse.Champion> champions);
    }

    interface ChampionPresenter extends BaseContract.BasePresenter {
        void fetchChampions();
        void saveChampion(ChampionResponse.Champion champion);
        void deleteChampion(ChampionResponse.Champion champion);
    }

    interface DetailsView extends BaseContract.BaseView {
        void displayChampionDetails(DetailsResponse.Detail championDetails);
    }

    interface DetailsPresenter extends BaseContract.BasePresenter {
        void fetchChampionDetails(String championId);
    }

    interface FavoriteView extends BaseContract.BaseView {
        void displayFavoriteChampions(List<ChampionResponse.Champion> champions);
        void displayNoFavorites();
    }

    interface FavoritePresenter extends BaseContract.BasePresenter {
        void getFavorites();
    }
}
