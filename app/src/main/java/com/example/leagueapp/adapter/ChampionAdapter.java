package com.example.leagueapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.leagueapp.R;
import com.example.leagueapp.model.ChampionResponse;

import java.util.ArrayList;


public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ViewHolder> {

    private OnChampClickListener onChampClickListener;
    private ArrayList<ChampionResponse.Champion> champions = new ArrayList<>();

    public ChampionAdapter(OnChampClickListener onChampClickListener) {
        this.onChampClickListener = onChampClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnChampClickListener onChampClickListener;
        final ImageView championIcon;
        final TextView championName;
        final TextView championTitle;


        ViewHolder(@NonNull View itemView, OnChampClickListener onChampClickListener) {
            super(itemView);
            this.onChampClickListener = onChampClickListener;
            championIcon = itemView.findViewById(R.id.championIcon);
            championName = itemView.findViewById(R.id.championName);
            championTitle = itemView.findViewById(R.id.championTitle);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.favoriteButton).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.favoriteButton) {
                ChampionResponse.Champion champion = champions.get(getAdapterPosition());
                onChampClickListener.addToFavorite(champion);
            } else {
                String name = championName.getText().toString();
                onChampClickListener.onChampClick(name);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.champion_item, parent, false);
        return new ViewHolder(itemView, onChampClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChampionResponse.Champion currentChampion = champions.get(position);
        Glide.with(holder.championIcon)
                .load(currentChampion.getImage().getIconUrl())
                .into(holder.championIcon);
        holder.championName.setText(currentChampion.getName());
        holder.championTitle.setText(currentChampion.getTitle());
    }

    @Override
    public int getItemCount() {
        return champions.size();
    }

    public void setChampionList(ArrayList<ChampionResponse.Champion> championList) {
        this.champions = championList;
        notifyItemRangeChanged(0, championList.size());
    }

    public Boolean hasChampions() {
        return !champions.isEmpty();
    }

    public interface OnChampClickListener {
        void onChampClick(String championName);
        void addToFavorite(ChampionResponse.Champion champion);
    }
}
