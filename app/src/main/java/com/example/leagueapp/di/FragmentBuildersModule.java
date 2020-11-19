package com.example.leagueapp.di;

import com.example.leagueapp.view.ChampionsFragment;
import com.example.leagueapp.view.DetailsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ChampionsFragment contributeChampionsFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment contributeDetailsFragment();
}
