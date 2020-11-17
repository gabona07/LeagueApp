package com.example.leagueapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "champions")
public class ChampionEntity {

    @PrimaryKey
    @NonNull
    String id;
    long key;
    String name;
    String title;
    String image;

    public ChampionEntity(@NonNull String id, long key, String name, String title, String image) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.title = title;
        this.image = image;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
