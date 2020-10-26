package com.example.leagueapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.leagueapp.R;
import com.example.leagueapp.model.ChampionResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ViewHolder> {

    private ArrayList<ChampionResponse.Champion> champions = new ArrayList<>();
    private Picasso picasso = Picasso.get();

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView championIcon;
        final TextView championName;
        final TextView championTitle;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            championIcon = itemView.findViewById(R.id.championIcon);
            championName = itemView.findViewById(R.id.championName);
            championTitle = itemView.findViewById(R.id.championTitle);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.champion_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChampionResponse.Champion currentChampion = champions.get(position);
        picasso.load(currentChampion.getImage().getIconUrl()).into(holder.championIcon);
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
}
