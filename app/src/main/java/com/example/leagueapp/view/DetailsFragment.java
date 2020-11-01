package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.leagueapp.R;
import com.example.leagueapp.contract.ChampionContract;
import com.example.leagueapp.model.DetailsResponse;
import com.example.leagueapp.presenter.DetailsPresenter;


public class DetailsFragment extends Fragment implements ChampionContract.DetailsView {

    private static final String TAG = "DetailsFragment";
    private ImageView profileImage;
    private TextView championName;
    private ChampionContract.DetailsPresenter presenter = new DetailsPresenter();

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter.onAttach(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            profileImage = view.findViewById(R.id.profileImage);
            championName = view.findViewById(R.id.championName);
            String championName = DetailsFragmentArgs.fromBundle(getArguments()).getChampionName();
            presenter.fetchChampionDetails(championName);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            NavDirections action = DetailsFragmentDirections.actionDetailsFragmentToFavoriteFragment();
            NavHostFragment.findNavController(this).navigate(action);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void displayChampionDetails(DetailsResponse.Detail champion) {
        Log.d(TAG, "displayChampionDetails: " + champion.getImage().getFull());
        Glide.with(profileImage).load(champion.getImage().getIconUrl()).into(profileImage);
        championName.setText(champion.getName());
    }

    @Override
    public void showLoading() { }

    @Override
    public void hideLoading() { }

    @Override
    public void onError(Exception exception) {

    }
}