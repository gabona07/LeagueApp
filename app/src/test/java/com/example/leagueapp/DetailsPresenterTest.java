package com.example.leagueapp;


import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.DataManager;
import com.example.leagueapp.model.DetailsResponse;
import com.example.leagueapp.presenter.DetailsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
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
public class DetailsPresenterTest {
    private ChampionContract.DetailsView mockedView = mock(ChampionContract.DetailsView.class);
    private DataManager mockedDataManager = mock(DataManager.class);
    private DetailsPresenter presenter = new DetailsPresenter(mockedDataManager);
    private DetailsResponse fakeResponse;
    private DetailsResponse.Detail fakeDetails;

    @Before
    public void setup() {

        String lore = "Once honored defenders of Shurima against the Void, Aatrox and his brethren would eventually become an even greater threat to Runeterra, and were defeated only by cunning mortal sorcery. But after centuries of imprisonment, Aatrox was the first to find freedom once more, corrupting and transforming those foolish enough to try and wield the magical weapon that contained his essence. Now, with stolen flesh, he walks Runeterra in a brutal approximation of his previous form, seeking an apocalyptic and long overdue vengeance.";
        DetailsResponse.Detail.Info fakeInfo = new DetailsResponse.Detail.Info(8,4,3,4);
        List<DetailsResponse.Detail.Spell> fakeSpells = new ArrayList<>();

        DetailsResponse.Detail.Spell fakeSpell1 = new DetailsResponse.Detail.Spell("The Darkin Blade", "Aatrox slams his greatsword down, dealing physical damage. He can swing three times, each with a different area of effect.", new DetailsResponse.Detail.Spell.Image("AatroxQ.png"));
        DetailsResponse.Detail.Spell fakeSpell2 = new DetailsResponse.Detail.Spell("Infernal Chains", "Aatrox smashes the ground, dealing damage to the first enemy hit. Champions and large monsters have to leave the impact area quickly or they will be dragged to the center and take the damage again.", new DetailsResponse.Detail.Spell.Image("AatroxW.png"));
        DetailsResponse.Detail.Spell fakeSpell3 = new DetailsResponse.Detail.Spell("Umbral Dash", "Passively, Aatrox heals when damaging enemy champions. On activation, he dashes in a direction.", new DetailsResponse.Detail.Spell.Image("AatroxE.png"));
        DetailsResponse.Detail.Spell fakeSpell4 = new DetailsResponse.Detail.Spell("World Ender", "Aatrox unleashes his demonic form, fearing nearby enemy minions and gaining attack damage, increased healing, and movement speed. If he gets a takedown, this effect is extended.", new DetailsResponse.Detail.Spell.Image("AatroxT.png"));

        fakeSpells.add(fakeSpell1);
        fakeSpells.add(fakeSpell2);
        fakeSpells.add(fakeSpell3);
        fakeSpells.add(fakeSpell4);

        fakeDetails = new DetailsResponse.Detail(lore, fakeInfo, fakeSpells);

        Map<String, DetailsResponse.Detail> fakeData = new HashMap<>();
        fakeData.put("Aatrox", fakeDetails);

        fakeResponse = new DetailsResponse(fakeData);

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
    public void getDetailsSuccessfulNetWorkCall() {
        // Given
        when(mockedDataManager.getChampionDetails("Aatrox")).thenReturn(Single.just(fakeResponse));

        // When
        presenter.fetchChampionDetails("Aatrox");

        // Should
        verify(mockedView).hideLoading();
        verify(mockedView).showLoading();
        verify(mockedView).displayChampionDetails(fakeDetails);
    }
}
