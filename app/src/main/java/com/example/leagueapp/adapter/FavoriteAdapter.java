package com.example.leagueapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.leagueapp.R;
import com.example.leagueapp.model.ChampionResponse;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<ChampionResponse.Champion> champions = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView favoriteImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favoriteImage = itemView.findViewById(R.id.favoriteImage);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChampionResponse.Champion currentFavorite = champions.get(position);
        Glide.with(holder.favoriteImage)
                .load(currentFavorite.getChampLoadingImage())
                .into(holder.favoriteImage);
    }

    @Override
    public int getItemCount() {
        return champions.size();
    }

    public void setChampions(List<ChampionResponse.Champion> champions) {
        this.champions = champions;
        notifyDataSetChanged();
    }

}
