package com.example.leagueapp.presenter;

import com.example.leagueapp.contract.BaseContract;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DataManager;
import java.util.ArrayList;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChampionPresenter implements ChampionContract.ChampionPresenter {

    private ChampionContract.ChampionView view;
    private Disposable disposable;
    private DataManager dataManager = new DataManager();

    @Override
    public void fetchChampions() {
        view.showLoading();
        dataManager.getChampions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ChampionResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(ChampionResponse championResponse) {
                view.hideLoading();
                ArrayList<ChampionResponse.Champion> champions = new ArrayList<>(championResponse.data.values());
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
