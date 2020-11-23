package com.example.leagueapp.model;

import com.example.leagueapp.database.ChampionDao;
import com.example.leagueapp.database.ChampionEntity;
import com.example.leagueapp.database.ChampionMapperKt;
import com.example.leagueapp.network.LeagueOfLegendsApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DataManager {

    private LeagueOfLegendsApi apiService;
    private ChampionDao championDao;

    public DataManager(LeagueOfLegendsApi apiService, ChampionDao championDao) {
        this.apiService = apiService;
        this.championDao = championDao;
    }

    public Single<List<ChampionResponse.Champion>> getChampions() {
        return Single.zip(apiService.getChampions(), championDao.getFavorites(), (championResponse, championEntities) -> {
            List<String> favoriteChampionIds = ChampionMapperKt.toChampionIds(championEntities);
            List<ChampionResponse.Champion> champions = new ArrayList<>(championResponse.data.values());
            for (ChampionResponse.Champion champion : champions) {
                if (favoriteChampionIds.contains(champion.id)) {
                    champion.isFavorite = true;
                }
            }
            return champions;
        });
    }

    public Single<DetailsResponse> getChampionDetails(String championId) {return apiService.getChampionDetails(championId);}

    public Single<List<ChampionEntity>> getFavoriteChampions() {
        return championDao.getFavorites();
    }

    public Completable addChampionToFavorites(ChampionEntity championEntity) {
        return championDao.addToFavorites(championEntity);
    }

    public Completable deleteChampionFromFavorites(ChampionEntity championEntity) {
        return championDao.delete(championEntity);
    }

}
