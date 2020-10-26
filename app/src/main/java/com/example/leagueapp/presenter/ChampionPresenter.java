package com.example.leagueapp.presenter;


import com.example.leagueapp.ApiService;
import com.example.leagueapp.RetrofitProvider;
import com.example.leagueapp.contract.BaseContract;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ChampionPresenter implements ChampionContract.ChampionPresenter {

    private ChampionContract.ChampionView view;
    private Disposable disposable;
    private Retrofit retrofit = RetrofitProvider.retrofit;

    @Override
    public void fetchChampions() {
        Single<ChampionResponse> championResponse = retrofit.create(ApiService.class).getChampions();
        championResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ChampionResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ChampionResponse championResponse) {
                ArrayList<ChampionResponse.Champion> champions = new ArrayList<>(championResponse.data.values());
                view.displayChampions(champions);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.toString());
            }
        });

    }

    @Override
    public void onAttach(BaseContract.BaseView view) {
        this.view = (ChampionContract.ChampionView) view;
    }

    @Override
    public void onDetach() {
        this.view = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
