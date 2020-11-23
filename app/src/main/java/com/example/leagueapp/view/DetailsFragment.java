package com.example.leagueapp.view;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.leagueapp.R;
import com.example.leagueapp.adapter.SpellsAdapter;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.databinding.FragmentDetailsBinding;
import com.example.leagueapp.model.ChampionResponse;
import com.example.leagueapp.model.DetailsResponse;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.android.material.transition.MaterialFadeThrough;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class DetailsFragment extends DaggerFragment implements ChampionContract.DetailsView {

    private static final String TAG = "DetailsFragment";

    @Inject ChampionContract.DetailsPresenter detailsPresenter;
    @Inject RequestManager requestManager;
    private FragmentDetailsBinding binding;
    private SpellsAdapter spellsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        detailsPresenter.onAttach(this);
        spellsAdapter = new SpellsAdapter(requestManager);

        MaterialContainerTransform sharedElementTransition = new MaterialContainerTransform();
        long duration = getResources().getInteger(R.integer.reply_motion_duration);
        sharedElementTransition.setDuration(duration);
        sharedElementTransition.setScrimColor(Color.TRANSPARENT);
        sharedElementTransition.setDrawingViewId(R.id.navHostFragment);
        setSharedElementEnterTransition(sharedElementTransition);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            final ChampionResponse.Champion champion = DetailsFragmentArgs.fromBundle(getArguments()).getChampion();
            displayChampionBaseInfo(champion);
            binding.error.retryButton.setOnClickListener(view1 -> {
                view1.setVisibility(View.INVISIBLE);
                binding.error.retryLoading.setVisibility(View.VISIBLE);
                detailsPresenter.fetchChampionDetails(champion.id);
            });
            detailsPresenter.fetchChampionDetails(champion.id);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            MaterialFadeThrough exitTransition = new MaterialFadeThrough();
            long duration = getResources().getInteger(R.integer.reply_motion_duration);
            exitTransition.setDuration(duration);
            setExitTransition(exitTransition);
            NavDirections action = DetailsFragmentDirections.actionDetailsFragmentToFavoriteFragment();
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
        detailsPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void displayChampionDetails(DetailsResponse.Detail championDetails) {
        binding.error.errorContainer.setVisibility(View.GONE);
        binding.championLore.setText(championDetails.lore);
        spellsAdapter.setSpells(championDetails.spells);
        binding.spellsRecyclerView.setAdapter(spellsAdapter);
        binding.spellsRecyclerView.setHasFixedSize(true);
        binding.championDetailsContainer.setVisibility(View.VISIBLE);
        animateProgress(binding.attackBar, championDetails.info.getAttackProgress());
        animateProgress(binding.defenseBar, championDetails.info.getDefenseProgress());
        animateProgress(binding.magicBar, championDetails.info.getMagicProgress());
        animateProgress(binding.difficultyBar, championDetails.info.getDifficultyProgress());
    }

    @Override
    public void showLoading() {
        binding.detailsLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.detailsLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception exception) {
        Log.d(TAG, "onError: " + exception.getMessage());
        binding.error.errorContainer.setBackgroundColor(getResources().getColor(R.color.colorSurface));
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

    private void displayChampionBaseInfo(ChampionResponse.Champion champion) {
        ChampionResponse.Champion.Image championImage = champion.image;
        if (championImage != null) {
            Glide.with(binding.detailedChampionIcon).load(championImage.getIconUrl()).into(binding.detailedChampionIcon);
        }
        binding.detailedChampionName.setText(champion.name);
        binding.detailedChampionTitle.setText(champion.title);
        binding.championTags.setText(champion.getTags());

    }

    private void animateProgress(ProgressBar progressBar, int progress) {
        long duration = getResources().getInteger(R.integer.progress_motion_duration);
        long delay = getResources().getInteger(R.integer.progress_motion_delay);
        ObjectAnimator defenseBarAnimation = ObjectAnimator.ofInt(progressBar, "progress", progress);
        defenseBarAnimation.setInterpolator(new FastOutSlowInInterpolator());
        defenseBarAnimation.setStartDelay(delay);
        defenseBarAnimation.setDuration(duration);
        defenseBarAnimation.start();
    }
}