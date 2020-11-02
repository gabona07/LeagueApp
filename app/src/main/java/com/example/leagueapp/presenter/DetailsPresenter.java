package com.example.leagueapp.presenter;

import com.example.leagueapp.contract.BaseContract;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.DataManager;
import com.example.leagueapp.model.DetailsResponse;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsPresenter implements ChampionContract.DetailsPresenter {

    private ChampionContract.DetailsView view;
    private Disposable disposable;
    private DataManager dataManager = new DataManager();

    public void fetchChampionDetails(final String championId) {
        view.showLoading();
        dataManager.getChampionDetails(championId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<DetailsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(DetailsResponse detailsResponse) {
                        view.hideLoading();
                        DetailsResponse.Detail championDetails = detailsResponse.data.get(championId);
                        view.displayChampionDetails(championDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(e.toString());
                    }
                });
    }

    @Override
    public void onAttach(BaseContract.BaseView view) {
        this.view = (ChampionContract.DetailsView) view;
    }

    @Override
    public void onDetach() {
        this.view = null;
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
