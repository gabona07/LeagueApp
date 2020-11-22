package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leagueapp.R;
import com.example.leagueapp.adapter.FavoriteAdapter;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.databinding.FragmentFavoriteBinding;
import com.example.leagueapp.model.ChampionResponse;
import com.google.android.material.transition.MaterialFadeThrough;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FavoriteFragment extends DaggerFragment implements ChampionContract.FavoriteView {

    private static final String TAG = "FavoriteFragment";

    @Inject ChampionContract.FavoritePresenter favoritePresenter;
    private FragmentFavoriteBinding binding;
    private FavoriteAdapter adapter = new FavoriteAdapter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritePresenter.onAttach(this);
        MaterialFadeThrough enterTransition = new MaterialFadeThrough();
        long duration = getResources().getInteger(R.integer.reply_motion_duration);
        enterTransition.setDuration(duration);
        setEnterTransition(enterTransition);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        binding.favoriteViewpager.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritePresenter.getFavorites();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        favoritePresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void displayFavoriteChampions(List<ChampionResponse.Champion> champions) {
        binding.favoriteViewpager.setOffscreenPageLimit(3);
        binding.favoriteViewpager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer pageTransformer = new CompositePageTransformer();
        int champCardMargin = getResources().getInteger(R.integer.viewpager_card_margin);
        pageTransformer.addTransformer(new MarginPageTransformer(champCardMargin));
        pageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        binding.favoriteViewpager.setPageTransformer(pageTransformer);
        adapter.setChampions(champions);
    }

    @Override
    public void displayNoFavorites() {
        Log.d(TAG, "displayNoFavorites: NO FAVORITES");
    }

    @Override
    public void showLoading() {
        binding.favoriteLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.favoriteLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception exception) {

    }
}