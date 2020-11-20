package com.example.leagueapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChampionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavorites(ChampionEntity champion);

    @Delete
    void delete(ChampionEntity champion);

    @Query("SELECT * FROM favorites_table")
    List<ChampionEntity> getFavorites();
}
