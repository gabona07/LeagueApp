package com.example.leagueapp.di;

import android.app.Application;

import androidx.room.Room;

import com.example.leagueapp.database.ChampionDao;
import com.example.leagueapp.database.ChampionDatabase;
import com.example.leagueapp.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    @Singleton
    @Provides
    static ChampionDatabase provideChampionDatabase(Application application) {
        return Room.databaseBuilder(application, ChampionDatabase.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static ChampionDao provideChampionDao(ChampionDatabase db) {
        return db.championDao();
    }
}
