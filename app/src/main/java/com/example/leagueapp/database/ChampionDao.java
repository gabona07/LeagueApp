package com.example.leagueapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ChampionDao {

    @Insert
    void insert(ChampionEntity champion);

    @Delete
    void delete(ChampionEntity champion);

    @Query("SELECT * FROM champions")
    List<ChampionEntity> getChampions();
}
