package com.example.leagueapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface ChampionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToFavorites(ChampionEntity champion);

    @Delete
    Completable delete(ChampionEntity champion);

    @Query("SELECT * FROM favorites_table")
    Single<List<ChampionEntity>> getFavorites();
}
