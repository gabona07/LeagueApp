package com.example.leagueapp.contract;

public interface ChampionContract {

    interface ChampionView extends BaseContract.BaseView {

    }

    interface ChampionPresenter extends BaseContract.BasePresenter {
        void fetchChampions();
    }
}
