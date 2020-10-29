package com.example.leagueapp.model;

import com.example.leagueapp.ApiService;
import com.example.leagueapp.RetrofitProvider;
import io.reactivex.Single;

public class DataManager {

    private ApiService apiService = RetrofitProvider.retrofit.create(ApiService.class);

    public Single<ChampionResponse> getChampions() {
        return apiService.getChampions();
    }
}
