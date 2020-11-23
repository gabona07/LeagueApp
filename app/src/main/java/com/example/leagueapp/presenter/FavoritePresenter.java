package com.example.leagueapp.presenter;

import com.example.leagueapp.contract.BaseContract;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.database.ChampionEntity;
import com.example.leagueapp.database.ChampionMapperKt;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DataManager;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoritePresenter implements ChampionContract.FavoritePresenter {

    private ChampionContract.FavoriteView view;
    private Disposable disposable;
    private DataManager dataManager;

    public FavoritePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getFavorites() {
        view.showLoading();
        dataManager.getFavoriteChampions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ChampionEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(List<ChampionEntity> championEntities) {
                        List<ChampionResponse.Champion> championList = ChampionMapperKt.toChampionList(championEntities);
                        view.hideLoading();
                        if (championList.isEmpty()) {
                            view.displayNoFavorites();
                        } else {
                            view.displayFavoriteChampions(championList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void onAttach(BaseContract.BaseView view) {
        this.view = (ChampionContract.FavoriteView) view;
    }

    @Override
    public void onDetach() {
        if (disposable != null) {
            disposable.dispose();
        }
        this.view = null;
    }
}
