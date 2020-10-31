package com.example.leagueapp;

import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DetailsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("champion.json")
    Single<ChampionResponse> getChampions();

    @GET("champion/{championName}.json" )
    Single<DetailsResponse> getChampionDetails(@Path("championName")String championName);
}
