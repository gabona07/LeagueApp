package com.example.leagueapp;

import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DetailsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/cdn/10.21.1/data/en_US/champion.json")
    Single<ChampionResponse> getChampions();

    @GET("/cdn/10.21.1/data/en_US/champion/{championId}.json" )
    Single<DetailsResponse> getChampionDetails(@Path("championId")String championName);
}
