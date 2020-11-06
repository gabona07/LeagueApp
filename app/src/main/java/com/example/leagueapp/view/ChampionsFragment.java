package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.leagueapp.databinding.FragmentChampionsBinding;
import com.example.leagueapp.widget.ChampionSearchView;
import com.example.leagueapp.R;
import com.example.leagueapp.adapter.ChampionAdapter;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.presenter.ChampionPresenter;
import com.google.android.material.transition.MaterialElevationScale;
import com.google.android.material.transition.MaterialFadeThrough;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.xml.datatype.Duration;


public class ChampionsFragment extends Fragment implements ChampionContract.ChampionView, ChampionAdapter.OnChampClickListener {

    private static final String TAG = "ChampionsFragment";
    private ChampionContract.ChampionPresenter championPresenter = new ChampionPresenter();
    private ChampionAdapter championAdapter;
    private FragmentChampionsBinding binding;

    public ChampionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        championAdapter = new ChampionAdapter(this);
        championPresenter.onAttach(this);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChampionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postponeEnterTransition();
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                startPostponedEnterTransition();
                return true;
            }
        });
        binding.championsRecyclerView.setAdapter(championAdapter);

        // Navigation Component always rebuilds the fragment's view,
        // so this is a workaround to prevent fetching the champions again (we could also use LiveData)
        if (!championAdapter.holdsChampions()) {
            championPresenter.fetchChampions();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.champions_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final ChampionSearchView searchView = (ChampionSearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                championAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            MaterialFadeThrough exitTransition = new MaterialFadeThrough();
            long duration = getResources().getInteger(R.integer.reply_motion_duration);
            exitTransition.setDuration(duration);
            setExitTransition(exitTransition);
            NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToFavoriteFragment();
            NavHostFragment.findNavController(this).navigate(action);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        championAdapter.onDestroy();
        championPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        binding.championsLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.championsLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception exception) {
        Log.d(TAG, "onError: " + exception.getMessage());
    }

    @Override
    public void displayChampions(ArrayList<ChampionResponse.Champion> champions) {
        championAdapter.setChampionList(champions);
    }

    @Override
    public void onChampClick(CardView cardView, ChampionResponse.Champion champion, String championName) {
        long duration = getResources().getInteger(R.integer.reply_motion_duration);
        MaterialElevationScale exitTransition = new MaterialElevationScale(false);
        exitTransition.setDuration(duration);
        setExitTransition(exitTransition);

        MaterialElevationScale reenterTransition = new MaterialElevationScale(true);
        reenterTransition.setDuration(duration);
        setReenterTransition(reenterTransition);

        String championCardTransitionName = getString(R.string.champion_item_card_transition_name);
        FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                .addSharedElement(cardView, championCardTransitionName)
                .build();

        NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToDetailsFragment(championName, champion);
        NavHostFragment.findNavController(this).navigate(action, extras);
    }

    @Override
    public void addToFavorite(ChampionResponse.Champion champion) {
        Log.d(TAG, "addToFavorite: " + champion.toString());
    }
}