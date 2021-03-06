package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
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

import com.bumptech.glide.RequestManager;
import com.example.leagueapp.databinding.FragmentChampionsBinding;
import com.example.leagueapp.widget.ChampionSearchView;
import com.example.leagueapp.R;
import com.example.leagueapp.adapter.ChampionAdapter;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.ChampionResponse;
import com.google.android.material.transition.MaterialElevationScale;
import com.google.android.material.transition.MaterialFadeThrough;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ChampionsFragment extends DaggerFragment implements ChampionContract.ChampionView, ChampionAdapter.OnChampClickListener {

    private static final String TAG = "ChampionsFragment";
    private final String SEARCH_QUERY_KEY = "SEARCH_QUERY_KEY";

    @Inject ChampionContract.ChampionPresenter championPresenter;
    @Inject RequestManager requestManager;
    private ChampionAdapter championAdapter;
    private FragmentChampionsBinding binding;
    private ChampionSearchView searchView;
    private String searchQuery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        championPresenter.onAttach(this);
        championAdapter = new ChampionAdapter(this, requestManager);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentChampionsBinding.inflate(inflater, container, false);
        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString(SEARCH_QUERY_KEY);
        }
        binding.error.retryButton.setOnClickListener(view -> {
            view.setVisibility(View.INVISIBLE);
            binding.error.retryLoading.setVisibility(View.VISIBLE);
            championPresenter.fetchChampions();
        });
        binding.championsRecyclerView.setAdapter(championAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postponeEnterTransition();
        view.getViewTreeObserver().addOnPreDrawListener(() -> {
            startPostponedEnterTransition();
            return true;
        });

        // Navigation Component always rebuilds the fragment's view,
        // so this is a workaround to prevent fetching the champions again (we could also use LiveData)
        if (!championAdapter.holdsChampions()) {
            championPresenter.fetchChampions();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (searchView != null) {
            outState.putString(SEARCH_QUERY_KEY, searchQuery);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.champions_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem favoriteItem = menu.findItem(R.id.action_favorite);
        searchView = (ChampionSearchView) searchItem.getActionView();
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                favoriteItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                favoriteItem.setVisible(true);
                return true;
            }
        });
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchItem.expandActionView();
            searchView.setQuery(searchQuery, false);
            searchView.clearFocus();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                championAdapter.getFilter().filter(newText);
                return true;
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
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        championPresenter.onDetach();
        championAdapter.onDestroy();
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
        binding.error.errorContainer.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        if (exception.getCause() instanceof UnknownHostException) {
            binding.error.errorImage.setImageResource(R.drawable.error_connection);
            binding.error.errorTitle.setText(R.string.connection_error_title);
            binding.error.errorMessage.setText(R.string.connection_error_message);
        } else {
            binding.error.errorImage.setImageResource(R.drawable.error_generic);
            binding.error.errorTitle.setText(R.string.generic_error_title);
            binding.error.errorMessage.setText(R.string.generic_error_message);
        }
        binding.error.retryLoading.setVisibility(View.INVISIBLE);
        binding.error.retryButton.setVisibility(View.VISIBLE);
        binding.error.errorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayChampions(List<ChampionResponse.Champion> champions) {
        binding.error.errorContainer.setVisibility(View.GONE);
        championAdapter.setChampionList(champions);
    }

    @Override
    public void onChampCardClick(CardView cardView, ChampionResponse.Champion champion, String championName) {
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

        searchView.setOnQueryTextListener(null);

        NavDirections action = ChampionsFragmentDirections.actionChampionsFragmentToDetailsFragment(championName, champion);
        NavHostFragment.findNavController(this).navigate(action, extras);
    }

    @Override
    public void addToFavorites(ChampionResponse.Champion champion) {
        championPresenter.saveChampion(champion);
    }

    @Override
    public void removeFromFavorites(ChampionResponse.Champion champion) {
        championPresenter.deleteChampion(champion);
    }
}