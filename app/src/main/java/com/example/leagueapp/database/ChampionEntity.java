package com.example.leagueapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "favorites_table")
public class ChampionEntity {

    public ChampionEntity(@NotNull String id, Long key, String name, String title, String full, String tags) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.title = title;
        this.full = full;
        this.tags = tags;
    }

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @NonNull
    public String id;

    @ColumnInfo(name = "key")
    public Long key;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "full")
    String full;

    @ColumnInfo(name = "tags")
    String tags;
}
