package com.example.leagueapp.contract;

public interface BaseContract {

    interface BasePresenter {
        void onAttach(BaseView view);
        void onDetach();
    }

    interface BaseView {
        void onError(Exception exception);
    }
}
