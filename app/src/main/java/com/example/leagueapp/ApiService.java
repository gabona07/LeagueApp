package com.example.leagueapp;

import com.example.leagueapp.model.ChampionResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("champion.json")
    Single<ChampionResponse> getChampions();
}
