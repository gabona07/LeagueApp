package com.example.leagueapp.presenter;

import com.example.leagueapp.contract.BaseContract;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.database.ChampionEntity;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DataManager;
import com.example.leagueapp.database.ChampionMapperKt;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChampionPresenter implements ChampionContract.ChampionPresenter {

    private ChampionContract.ChampionView view;
    private Disposable disposable;
    private DataManager dataManager;

    public ChampionPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void fetchChampions() {
        view.showLoading();
        dataManager.getChampions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ChampionResponse.Champion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(List<ChampionResponse.Champion> champions) {
                        view.hideLoading();
                        view.displayChampions(champions);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Exception exception = new Exception(e);
                        view.hideLoading();
                        view.onError(exception);
                    }
                });
    }

    @Override
    public void saveChampion(ChampionResponse.Champion champion) {
        ChampionEntity championEntity = ChampionMapperKt.toChampionEntity(champion);
        dataManager.addChampionToFavorites(championEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void deleteChampion(ChampionResponse.Champion champion) {
        ChampionEntity championEntity = ChampionMapperKt.toChampionEntity(champion);
        dataManager.deleteChampionFromFavorites(championEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

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
