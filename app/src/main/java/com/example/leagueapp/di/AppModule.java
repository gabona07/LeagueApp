package com.example.leagueapp.di;

import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.DataManager;
import com.example.leagueapp.network.LeagueOfLegendsApi;
import com.example.leagueapp.presenter.ChampionPresenter;
import com.example.leagueapp.presenter.DetailsPresenter;
import com.example.leagueapp.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
    }

    @Singleton
    @Provides
    static LeagueOfLegendsApi provideLeagueOfLegendsApi(Retrofit retrofit) {
        return retrofit.create(LeagueOfLegendsApi.class);
    }

    @Singleton
    @Provides
    static DataManager provideDataManager(LeagueOfLegendsApi apiService) {
        return new DataManager(apiService);
    }

    @Singleton
    @Provides
    static ChampionContract.ChampionPresenter provideChampionPresenter(DataManager dataManager) {
        return new ChampionPresenter(dataManager);
    }

    @Singleton
    @Provides
    static ChampionContract.DetailsPresenter provideDetailsPresenter(DataManager dataManager) {
        return new DetailsPresenter(dataManager);
    }


}
