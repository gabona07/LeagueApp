package com.example.leagueapp;

import com.example.leagueapp.model.ChampionResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("cdn/10.21.1/data/en_US/champion.json")
    Single<ChampionResponse> getChampions();
}
