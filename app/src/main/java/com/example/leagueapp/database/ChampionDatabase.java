package com.example.leagueapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChampionEntity.class}, version = 1, exportSchema = false)
public abstract class ChampionDatabase extends RoomDatabase {

    public abstract ChampionDao championDao();
}
