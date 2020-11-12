package com.example.leagueapp;

import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DataManager;
import com.example.leagueapp.presenter.ChampionPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.internal.verification.NoMoreInteractions;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

@RunWith(MockitoJUnitRunner.class)
public class ChampionPresenterTest {

    private ChampionContract.ChampionView mockedView = mock(ChampionContract.ChampionView.class);
    private DataManager mockedDataManager = mock(DataManager.class);
    private ChampionPresenter presenter = new ChampionPresenter(mockedDataManager);
    private ChampionResponse fakeResponse;

    @Before
    public void setup() {
        List<String> fakeTags = new ArrayList<>();
        fakeTags.add("Fighter");
        fakeTags.add("Tank");

        ChampionResponse.Champion fakeChampion = new ChampionResponse.Champion("Aatrox", 266, "Aatrox", "the Darkin Blade", new ChampionResponse.Champion.Image("Aatrox.png"), fakeTags);

        Map<String, ChampionResponse.Champion> fakeData = new HashMap<>();
        fakeData.put("Aatrox", fakeChampion);

        fakeResponse = new ChampionResponse(fakeData);

        presenter.onAttach(mockedView);

        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulers) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @Test
    public void getChampionsSuccessfulNetWorkCall() {
        //Given
        when(mockedDataManager.getChampions()).thenReturn(Single.just(fakeResponse));

        //When
        presenter.fetchChampions();

        //Should
        verify(mockedView).showLoading();
        verify(mockedView).hideLoading();
        verify(mockedView).displayChampions(new ArrayList<>(fakeResponse.data.values()));
    }
}