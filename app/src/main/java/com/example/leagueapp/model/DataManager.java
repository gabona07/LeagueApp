package com.example.leagueapp.model;

import com.example.leagueapp.network.LeagueOfLegendsApi;

import io.reactivex.Single;

public class DataManager {

    private LeagueOfLegendsApi apiService;

    public DataManager(LeagueOfLegendsApi apiService) {
        this.apiService = apiService;
    }

    public Single<ChampionResponse> getChampions() {
        return apiService.getChampions();
    }

    public Single<DetailsResponse> getChampionDetails(String championId) {return apiService.getChampionDetails(championId);}

}
