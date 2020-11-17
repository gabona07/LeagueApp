package com.example.leagueapp.model;

import androidx.room.Room;

import com.example.leagueapp.ApiService;
import com.example.leagueapp.RetrofitProvider;
import com.example.leagueapp.database.AppDatabase;
import com.example.leagueapp.database.ChampionDao;

import io.reactivex.Single;

public class DataManager {

    private ApiService apiService = RetrofitProvider.retrofit.create(ApiService.class);

    public Single<ChampionResponse> getChampions() {
        return apiService.getChampions();
    }

    public Single<DetailsResponse> getChampionDetails(String championId) {return apiService.getChampionDetails(championId);}

}
