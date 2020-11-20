package com.example.leagueapp.model;

import com.example.leagueapp.database.ChampionDao;
import com.example.leagueapp.network.LeagueOfLegendsApi;

import io.reactivex.Single;

public class DataManager {

    private LeagueOfLegendsApi apiService;
    private ChampionDao championDao;

    public DataManager(LeagueOfLegendsApi apiService, ChampionDao championDao) {
        this.apiService = apiService;
        this.championDao = championDao;
    }

    public Single<ChampionResponse> getChampions() {
        return apiService.getChampions();
    }

    public Single<DetailsResponse> getChampionDetails(String championId) {return apiService.getChampionDetails(championId);}

}
