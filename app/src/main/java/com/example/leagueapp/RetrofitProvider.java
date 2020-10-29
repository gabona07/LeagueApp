package com.example.leagueapp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://ddragon.leagueoflegends.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(new OkHttpClient())
            .build();
}
